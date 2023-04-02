package com.craftbase.orderapi.order.port;

import com.craftbase.orderapi.order.usecase.OrderRollback;

public interface OrderRollbackPort {
    void send(OrderRollback orderRollback);
}
