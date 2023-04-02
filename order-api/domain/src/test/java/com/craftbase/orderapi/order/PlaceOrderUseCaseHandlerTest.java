package com.craftbase.orderapi.order;

import com.craftbase.orderapi.adapters.*;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.common.rule.RuleRunner;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.dto.OrderProductDto;
import com.craftbase.orderapi.order.model.dto.OrderProductOptionDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.rule.ProductActivityRule;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;
import com.craftbase.orderapi.order.usecase.PlaceOrder;
import com.craftbase.orderapi.payment.model.dto.CreditCard;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceOrderUseCaseHandlerTest {
    private final OrderFakeAdapter orderFakeAdapter = new OrderFakeAdapter();
    private final PlaceOrderUseCaseHandler handler = new PlaceOrderUseCaseHandler(orderFakeAdapter,
            new BasketFakeAdapter(),
            new PaymentFakeAdapter(),
            new ProductFakeAdapter(),
            new PaymentGatewayTypeFakeAdapter(),
            new BasketLockFakeAdapter(),
            new RuleRunner<>(List.of(new ProductActivityRule(new BasketFakeAdapter(), new ProductFakeAdapter()))),
            new OrderRollbackFakeAdapter(orderFakeAdapter));

    @Test
    public void testPlaceOrder_WhenSuccessPayment_ReturnOrderId() {
        
        PlaceOrder placeOrder = PlaceOrder.builder()
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .card(CreditCard.builder()
                        .cardHolderName("John Doe")
                        .cardNumber("1234567890123456")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();

        handler.handle(placeOrder);
        GetOrdersByPage getOrdersByPage = GetOrdersByPage.builder().page(0).size(10).userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892")).build();
        OrderDto actualOrder = orderFakeAdapter.getOrdersByPage(getOrdersByPage).getContents().get(0);

        OrderDto expectedOrderDto = getExpectedOrderDto();
        assertEquals(expectedOrderDto.getPrice(), actualOrder.getPrice());
        assertEquals(expectedOrderDto.getPriceType(), actualOrder.getPriceType());
        assertEquals(expectedOrderDto.getUserId(), actualOrder.getUserId());
        assertEquals(expectedOrderDto.getUserAddressId(), actualOrder.getUserAddressId());
        assertEquals(expectedOrderDto.getRestaurantFranchisingId(), actualOrder.getRestaurantFranchisingId());
        assertEquals(expectedOrderDto.getBasketId(), actualOrder.getBasketId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getPrice(), actualOrder.getOrderProducts().get(0).getPrice());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getProductId(), actualOrder.getOrderProducts().get(0).getProductId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getName(), actualOrder.getOrderProducts().get(0).getName());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getPrice(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getPrice());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getProductOptionId(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getProductOptionId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getName(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getName());
        assertEquals(expectedOrderDto.getStatus(), actualOrder.getStatus());

    }

    private OrderDto getExpectedOrderDto() {
        return OrderDto.builder()
                .id(UUID.fromString("97bae7d8-c7e9-4bbe-aa25-70b01cbc919e"))
                .price(new BigDecimal("15.99"))
                .priceType(PriceType.TRY)
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .basketId(UUID.fromString("b4a1e188-99b2-4768-b375-a7a7259c94ab"))
                .orderProducts(Collections.singletonList(
                        OrderProductDto.builder()
                                .price(new BigDecimal("10.99"))
                                .productId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                                .name("Cheeseburger")
                                .orderProductOptions(Collections.singletonList(
                                        OrderProductOptionDto.builder()
                                                .price(new BigDecimal("5.0"))
                                                .productOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"))
                                                .name("Extra Cheese")
                                                .build()
                                ))
                                .build()
                ))
                .status(OrderStatus.RECEIVED)
                .build();
    }
}