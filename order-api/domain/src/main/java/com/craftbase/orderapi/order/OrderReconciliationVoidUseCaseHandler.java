package com.craftbase.orderapi.order;

import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.model.dto.Pagination;
import com.craftbase.orderapi.common.usecase.VoidUseCaseHandler;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.port.OrderPort;
import com.craftbase.orderapi.order.port.OrderRollbackPort;
import com.craftbase.orderapi.order.usecase.OrderReconciliation;
import com.craftbase.orderapi.order.usecase.OrderRollback;
import com.craftbase.orderapi.payment.model.dto.PaymentReportDto;
import com.craftbase.orderapi.payment.model.enums.OrderPaymentStatus;
import com.craftbase.orderapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class OrderReconciliationVoidUseCaseHandler implements VoidUseCaseHandler<OrderReconciliation> {

    private final OrderPort orderPort;
    private final PaymentPort paymentPort;
    private final OrderRollbackPort orderRollbackPort;
    private static final int MAX_RETRY_COUNT = 4;

    @Override
    public void handle(OrderReconciliation orderReconciliation) {
        if (orderReconciliation.getOrderIds() == null || orderReconciliation.getOrderIds().isEmpty()) {
            findRollbackOrderAndProcess();
        }
    }

    private void findRollbackOrderAndProcess() {
        boolean hasNext;
        Pagination pagination = Pagination.builder().page(0).size(10).build();
        do {
            PageDto<OrderDto> orderPage = orderPort
                    .getOrdersByStatusesAndRetryLessThanAndPage(List.of(OrderStatus.ROLLBACK),
                            MAX_RETRY_COUNT,
                            pagination);
            processReconciliationByOrders(orderPage.getContents());
            if (orderPage.getPage() < orderPage.getTotalPage()) {
                hasNext = true;
                pagination.setPage(pagination.getPage() + 1);
            } else {
                hasNext = false;
            }
        } while (hasNext);
    }

    private void processReconciliationByOrders(List<OrderDto> orders) {
        orders.forEach(orderDto -> {
            PaymentReportDto paymentReport = paymentPort.getPaymentReport(orderDto.getExternalPaymentTransactionId(), orderDto.getGatewayType());
            if (orderDto.getStatus().equals(OrderStatus.ROLLBACK) &&
                    paymentReport.getRefundStatus().equals(OrderPaymentStatus.NOT_REFUNDED)) {
                var orderRollback = OrderRollback.builder().orderId(orderDto.getId()).build();
                orderPort.updateOrderStatus(orderDto.getId(), OrderStatus.ROLLBACK_FAILED);
                log.info("Process payment to rollback orderId:{}, retryCount:{}", orderDto.getId(), orderDto.getRetryCount());
                orderRollbackPort.send(orderRollback);
            } else {
                orderPort.updateOrderStatus(orderDto.getId(), OrderStatus.ROLLBACK_SUCCESS);
            }
        });
    }
}
