package com.example.orderapi.order.port;

import com.example.orderapi.order.usecase.OrderRollback;

public interface OrderRollbackPort {
    void send(OrderRollback orderRollback);
}
