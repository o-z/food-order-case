package com.example.orderapi.adapters.order.jpa;

import com.example.orderapi.adapters.order.jpa.entity.OrderEntity;
import com.example.orderapi.adapters.order.jpa.entity.OrderProductEntity;
import com.example.orderapi.adapters.order.jpa.entity.OrderProductOptionEntity;
import com.example.orderapi.adapters.order.jpa.repository.OrderProductOptionRepository;
import com.example.orderapi.adapters.order.jpa.repository.OrderProductRepository;
import com.example.orderapi.adapters.order.jpa.repository.OrderRepository;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.common.model.dto.PageDto;
import com.example.orderapi.common.model.dto.Pagination;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.order.port.OrderPort;
import com.example.orderapi.order.usecase.CreateOrder;
import com.example.orderapi.order.usecase.CreateOrderProduct;
import com.example.orderapi.order.usecase.GetOrdersByPage;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class OrderDataAdapter implements OrderPort {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductOptionRepository orderProductOptionRepository;


    @Transactional
    @Override
    public OrderDto createOrder(CreateOrder createOrder) {

        OrderEntity orderEntity = generateOrderEntity(createOrder);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        List<OrderProductEntity> savedOrderProductEntities = createOrder.getOrderProducts().stream().map(product -> {

            OrderProductEntity orderProductEntity = generateOrderProductEntity(savedOrderEntity, product);
            OrderProductEntity savedOrderProductEntity = orderProductRepository.save(orderProductEntity);

            List<OrderProductOptionEntity> orderProductOptionEntities = generateOrderProductOptionEntities(product, savedOrderProductEntity);
            List<OrderProductOptionEntity> savedOrderProductOptionEntities = orderProductOptionRepository.saveAll(orderProductOptionEntities);
            savedOrderProductEntity.setOrderProductOptionEntities(savedOrderProductOptionEntities);
            return savedOrderProductEntity;
        }).collect(Collectors.toList());

        savedOrderEntity.setOrderProductEntities(savedOrderProductEntities);
        return savedOrderEntity.toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDto getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.ORDER_NOT_FOUND_ERROR))
                .toModel();
    }


    @Transactional(readOnly = true)
    @Override
    public PageDto<OrderDto> getOrdersByPage(GetOrdersByPage getOrdersByPage) {

        Pageable pageable = PageRequest.of(getOrdersByPage.getPage(), getOrdersByPage.getSize());

        Page<OrderEntity> allByUserId = orderRepository.findAllByUserIdOrderByCreateDateDesc(getOrdersByPage.getUserId(), pageable);
        List<OrderDto> orders = allByUserId.getContent().stream().map(OrderEntity::toModel).toList();
        return PageDto.<OrderDto>builder()
                .contents(orders)
                .page(allByUserId.getNumber())
                .size(allByUserId.getSize())
                .totalPage(allByUserId.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public PageDto<OrderDto> getOrdersByStatusesAndRetryLessThanAndPage(List<OrderStatus> orderStatuses, Integer maxRetryCount, Pagination pagination) {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Page<OrderEntity> pageOrderEntity = orderRepository.findAllByStatusInAndRetryCountLessThanOrderByCreateDateDesc(orderStatuses,
                maxRetryCount
                , pageable);
        List<OrderDto> orders = pageOrderEntity.getContent().stream().map(OrderEntity::toModel).toList();
        return PageDto.<OrderDto>builder()
                .contents(orders)
                .page(pageOrderEntity.getNumber())
                .size(pageOrderEntity.getSize())
                .totalPage(pageOrderEntity.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.ORDER_NOT_FOUND_ERROR));
        orderEntity.setStatus(orderStatus);
        return orderRepository.save(orderEntity).toModel();
    }

    @Transactional
    @Override
    public OrderDto updateOrder(UUID orderId, String externalPaymentTransactionId, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.ORDER_NOT_FOUND_ERROR));
        orderEntity.setStatus(orderStatus);
        orderEntity.setExternalPaymentTransactionId(externalPaymentTransactionId);
        return orderRepository.save(orderEntity).toModel();
    }

    @Transactional
    @Override
    public void updateOrderStatusAndIncreaseRetryCount(UUID orderId, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.ORDER_NOT_FOUND_ERROR));
        orderEntity.setStatus(orderStatus);
        orderEntity.setRetryCount(orderEntity.getRetryCount() + 1);
        orderRepository.save(orderEntity);
    }

    private OrderEntity generateOrderEntity(CreateOrder createOrder) {
        return OrderEntity.builder()
                .price(createOrder.getPrice())
                .priceType(createOrder.getPriceType())
                .userId(createOrder.getUserId())
                .userAddressId(createOrder.getUserAddressId())
                .restaurantFranchisingId(createOrder.getRestaurantFranchisingId())
                .gatewayType(createOrder.getGatewayType())
                .basketId(createOrder.getBasketId())
                .status(createOrder.getStatus())
                .build();
    }


    private List<OrderProductOptionEntity> generateOrderProductOptionEntities(CreateOrderProduct product,
                                                                              OrderProductEntity savedOrderProductEntity) {
        if (CollectionUtils.isEmpty(product.getOrderProductOptions())) {
            return Collections.emptyList();
        }
        return product.getOrderProductOptions().stream()
                .map(productOption -> OrderProductOptionEntity.builder()
                        .price(productOption.getPrice())
                        .productOptionId(productOption.getProductOptionId())
                        .productOptionName(productOption.getName())
                        .orderProductEntity(savedOrderProductEntity)
                        .build()).collect(Collectors.toList());
    }

    private OrderProductEntity generateOrderProductEntity(OrderEntity savedOrderEntity,
                                                          CreateOrderProduct product) {
        return OrderProductEntity.builder()
                .price(product.getPrice())
                .orderEntity(savedOrderEntity)
                .productId(product.getProductId())
                .productName(product.getName())
                .build();
    }

}
