package com.example.orderapi.order.port;

import com.example.orderapi.common.model.dto.PageDto;
import com.example.orderapi.common.model.dto.Pagination;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.order.usecase.CreateOrder;
import com.example.orderapi.order.usecase.GetOrdersByPage;
import java.util.List;
import java.util.UUID;

public interface OrderPort {
    OrderDto createOrder(CreateOrder createOrder);

    OrderDto getOrder(UUID orderId);

    PageDto<OrderDto> getOrdersByPage(GetOrdersByPage getOrdersByPage);

    PageDto<OrderDto> getOrdersByStatusesAndRetryLessThanAndPage(List<OrderStatus> orderStatuses,
                                                                 Integer maxRetryCount,
                                                                 Pagination pagination);

    OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    OrderDto updateOrder(UUID orderId,
                         String externalPaymentTransactionId,
                         OrderStatus orderStatus);

    void updateOrderStatusAndIncreaseRetryCount(UUID orderId, OrderStatus orderStatus);


}
