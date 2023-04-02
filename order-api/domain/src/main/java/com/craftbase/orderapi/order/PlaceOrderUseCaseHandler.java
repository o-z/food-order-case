package com.craftbase.orderapi.order;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.basket.model.dto.BasketProductDto;
import com.craftbase.orderapi.basket.port.BasketLockPort;
import com.craftbase.orderapi.basket.port.BasketPort;
import com.craftbase.orderapi.common.exception.ErrorCode;
import com.craftbase.orderapi.common.exception.OrderApiException;
import com.craftbase.orderapi.common.rule.RuleRunner;
import com.craftbase.orderapi.common.usecase.UseCaseHandler;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.port.OrderPort;
import com.craftbase.orderapi.order.port.OrderRollbackPort;
import com.craftbase.orderapi.order.usecase.CreateOrder;
import com.craftbase.orderapi.order.usecase.OrderRollback;
import com.craftbase.orderapi.order.usecase.PlaceOrder;
import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.port.PaymentGatewayTypePort;
import com.craftbase.orderapi.payment.port.PaymentPort;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import com.craftbase.orderapi.product.port.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class PlaceOrderUseCaseHandler implements UseCaseHandler<UUID, PlaceOrder> {

    private final OrderPort orderPort;
    private final BasketPort basketPort;
    private final PaymentPort paymentPort;
    private final ProductPort productPort;
    private final PaymentGatewayTypePort paymentGatewayTypePort;
    private final BasketLockPort basketLockPort;
    private final RuleRunner<PlaceOrder> ruleRunner;
    private final OrderRollbackPort orderRollbackPort;


    @Override
    public UUID handle(PlaceOrder placeOrder) {
        log.info("placeOrder started for userId : {}", placeOrder.getUserId());

        BasketDto basket = basketPort.getBasketByUserId(placeOrder.getUserId());
        if (basket.getBasketProducts().isEmpty()) {
            throw new OrderApiException(ErrorCode.BASKET_IS_EMPTY_ERROR);
        }
        ruleRunner.validate(placeOrder);
        GatewayType paymentGatewayType = paymentGatewayTypePort.getPaymentGatewayType();
        basketLockPort.lock(basket.getId());
        OrderDto orderDto = generateOrder(placeOrder, basket, paymentGatewayType);
        log.info("order generated for userId : {} orderId: {}", placeOrder.getUserId(), orderDto.getId());

        try {
            PaymentDto paymentDto = paymentPort.pay(PaymentCreate.from(placeOrder.getCard(), orderDto),
                    paymentGatewayType);
            processPaymentResult(orderDto, paymentDto);
        } catch (RuntimeException e) {
            log.error("Payment failed for orderId : {}", orderDto.getId(), e);
            orderPort.updateOrderStatus(orderDto.getId(), OrderStatus.PAYMENT_FAILED);
        } finally {
            basketLockPort.unlock(basket.getId());
        }
        return orderDto.getId();
    }

    private OrderDto generateOrder(PlaceOrder placeOrder, BasketDto basket, GatewayType gatewayType) {
        Set<UUID> productIds = basket.getBasketProducts().stream().map(BasketProductDto::getProductId).collect(Collectors.toSet());
        Map<UUID, ProductDto> productDtoMap = productPort.getProductByIdSet(productIds);

        BasketDto.setProductDataToBasket(basket, productDtoMap);
        return orderPort.createOrder(CreateOrder.from(placeOrder, basket, gatewayType));
    }


    private void processPaymentResult(OrderDto orderDto, PaymentDto paymentDto) {
        if (paymentDto.isSuccess()) {
            log.info("payment is success orderId: {}", orderDto.getId());
            try {
                orderPort.updateOrder(orderDto.getId(), paymentDto.getId(), OrderStatus.RECEIVED);
                basketPort.clearBasket(orderDto.getBasketId());
            } catch (RuntimeException e) {
                log.error("Process payment to rollback", e);
                var orderRollback = OrderRollback.builder().orderId(orderDto.getId()).build();
                orderRollbackPort.send(orderRollback);
            }
        } else {
            log.info("payment is failed orderId: {}", orderDto.getId());
            orderPort.updateOrderStatus(orderDto.getId(), OrderStatus.PAYMENT_FAILED);
        }
    }
}
