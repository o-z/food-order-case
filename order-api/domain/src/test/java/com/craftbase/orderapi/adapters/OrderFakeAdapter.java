package com.craftbase.orderapi.adapters;

import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.model.dto.Pagination;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.dto.OrderProductDto;
import com.craftbase.orderapi.order.model.dto.OrderProductOptionDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.port.OrderPort;
import com.craftbase.orderapi.order.usecase.CreateOrder;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class OrderFakeAdapter implements OrderPort {

    private final Map<UUID, OrderDto> orders = new HashMap<>();

    @Override
    public OrderDto createOrder(CreateOrder createOrder) {
        OrderDto order = OrderDto.builder()
                .id(UUID.randomUUID())
                .price(createOrder.getPrice())
                .priceType(createOrder.getPriceType())
                .userId(createOrder.getUserId())
                .userAddressId(createOrder.getUserAddressId())
                .restaurantFranchisingId(createOrder.getRestaurantFranchisingId())
                .basketId(createOrder.getBasketId())
                .orderProducts(createOrder.getOrderProducts().stream()
                        .map(orderProduct -> OrderProductDto.builder()
                                .id(UUID.randomUUID())
                                .price(orderProduct.getPrice())
                                .productId(orderProduct.getProductId())
                                .name(orderProduct.getName())
                                .orderProductOptions(orderProduct.getOrderProductOptions().stream()
                                        .map(orderProductOption -> OrderProductOptionDto.builder()
                                                .id(UUID.randomUUID())
                                                .price(orderProductOption.getPrice())
                                                .productOptionId(orderProductOption.getProductOptionId())
                                                .name(orderProductOption.getName())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .status(createOrder.getStatus())
                .build();
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public OrderDto getOrder(UUID orderId) {
        return orders.get(orderId);
    }

    @Override
    public PageDto<OrderDto> getOrdersByPage(GetOrdersByPage getOrdersByPage) {
        List<OrderDto> ordersByPage = orders.values().stream().toList();
        return new PageDto<>(ordersByPage, getOrdersByPage.getPage(), getOrdersByPage.getSize(), ordersByPage.size());
    }

    @Override
    public PageDto<OrderDto> getOrdersByStatusesAndRetryLessThanAndPage(List<OrderStatus> orderStatuses, Integer maxRetryCount, Pagination pagination) {
        List<OrderDto> ordersByPage = orders.values().stream()
                .filter(orderDto -> orderStatuses.contains(orderDto.getStatus()) && orderDto.getRetryCount() < maxRetryCount)
                .toList();
        return new PageDto<>(ordersByPage, pagination.getPage(), pagination.getSize(), ordersByPage.size());
    }

    @Override
    public OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        OrderDto order = orders.get(orderId);
        order.setStatus(orderStatus);
        orders.put(orderId, order);
        return order;
    }

    @Override
    public OrderDto updateOrder(UUID orderId, String externalPaymentTransactionId, OrderStatus orderStatus) {
        OrderDto order = orders.get(orderId);
        order.setStatus(orderStatus);
        order.setExternalPaymentTransactionId(externalPaymentTransactionId);
        orders.put(orderId, order);
        return order;
    }

    @Override
    public void updateOrderStatusAndIncreaseRetryCount(UUID orderId, OrderStatus orderStatus) {
        OrderDto order = orders.get(orderId);
        order.setStatus(orderStatus);
        order.setRetryCount(order.getRetryCount() + 1);
        orders.put(orderId, order);

    }
}