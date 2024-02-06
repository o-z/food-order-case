package com.example.orderapi.order;

import com.example.orderapi.common.usecase.VoidUseCaseHandler;
import com.example.orderapi.common.util.DomainComponent;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.order.port.OrderPort;
import com.example.orderapi.order.usecase.OrderRollback;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.usecase.PaymentRefund;
import com.example.orderapi.payment.port.PaymentPort;
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
