package com.craftbase.orderapi.order.port;

import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.model.dto.Pagination;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.usecase.CreateOrder;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;

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
