package com.craftbase.orderapi.adapters;

import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.port.OrderRollbackPort;
import com.craftbase.orderapi.order.usecase.OrderRollback;

public class OrderRollbackFakeAdapter implements OrderRollbackPort {

    private final OrderFakeAdapter orderFakeAdapter;

    public OrderRollbackFakeAdapter(OrderFakeAdapter orderFakeAdapter) {
        this.orderFakeAdapter = orderFakeAdapter;
    }

    @Override
    public void send(OrderRollback orderRollback) {
        orderFakeAdapter.updateOrderStatus(orderRollback.getOrderId(), OrderStatus.PAYMENT_FAILED);
    }
}
