package com.craftbase.orderapi.order;

import com.craftbase.orderapi.common.usecase.VoidUseCaseHandler;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.port.OrderPort;
import com.craftbase.orderapi.order.usecase.OrderRollback;
import com.craftbase.orderapi.payment.model.dto.PaymentRefundDto;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;
import com.craftbase.orderapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class OrderRollbackVoidUseCaseHandler implements VoidUseCaseHandler<OrderRollback> {

    private final OrderPort orderPort;
    private final PaymentPort paymentPort;
    private static final int MAX_RETRY_COUNT = 4;


    @Override
    public void handle(OrderRollback orderRollback) {
        OrderDto orderDto = orderPort.getOrder(orderRollback.getOrderId());
        if (orderDto.getStatus().equals(OrderStatus.ROLLBACK)) {
            return;
        }
        var paymentRefund = PaymentRefund.builder().paymentId(orderDto.getExternalPaymentTransactionId()).build();
        try {
            PaymentRefundDto refund = paymentPort.refund(paymentRefund, orderDto.getGatewayType());
            if (refund.isSuccess()) {
                orderPort.updateOrderStatusAndIncreaseRetryCount(orderDto.getId(), OrderStatus.ROLLBACK);
            } else if (!refund.isSuccess() && orderDto.getRetryCount() > MAX_RETRY_COUNT) {
                orderPort.updateOrderStatus(orderDto.getId(), OrderStatus.ROLLBACK_FAILED);
            }
        } catch (RuntimeException e) {
            log.error("OrderRollback with orderId:{} is not rollback e:{}", orderDto.getId(), e);
        }
    }
}
