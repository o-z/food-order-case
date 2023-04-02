package com.craftbase.orderapi.adapters.order;

import com.craftbase.orderapi.adapters.order.jpa.OrderDataAdapter;
import com.craftbase.orderapi.adapters.order.jpa.entity.OrderEntity;
import com.craftbase.orderapi.adapters.order.jpa.entity.OrderProductEntity;
import com.craftbase.orderapi.adapters.order.jpa.entity.OrderProductOptionEntity;
import com.craftbase.orderapi.adapters.order.jpa.repository.OrderProductOptionRepository;
import com.craftbase.orderapi.adapters.order.jpa.repository.OrderProductRepository;
import com.craftbase.orderapi.adapters.order.jpa.repository.OrderRepository;
import com.craftbase.orderapi.common.exception.OrderApiException;
import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.model.dto.Pagination;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.usecase.CreateOrder;
import com.craftbase.orderapi.order.usecase.CreateOrderProduct;
import com.craftbase.orderapi.order.usecase.CreateOrderProductOption;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDataAdapterTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderProductOptionRepository orderProductOptionRepository;

    @InjectMocks
    private OrderDataAdapter orderDataAdapter;

    @Test
    public void testCreateOrder_WhenOrderIsCreated_ThenReturnOrderDto() {
        CreateOrder createOrder = new CreateOrder();
        createOrder.setPrice(BigDecimal.valueOf(100));
        createOrder.setPriceType(PriceType.TRY);
        createOrder.setUserId(UUID.randomUUID());
        createOrder.setUserAddressId(UUID.randomUUID());
        createOrder.setRestaurantFranchisingId(UUID.randomUUID());
        createOrder.setBasketId(UUID.randomUUID());
        createOrder.setStatus(OrderStatus.INITIAL);

        List<CreateOrderProduct> orderProducts = new ArrayList<>();
        CreateOrderProduct orderProduct = new CreateOrderProduct();
        orderProduct.setName("product");
        orderProduct.setPrice(BigDecimal.valueOf(50));
        orderProduct.setProductId(UUID.randomUUID());
        List<CreateOrderProductOption> createOrderProductOptions = new ArrayList<>();
        CreateOrderProductOption createOrderProductOption = new CreateOrderProductOption();
        createOrderProductOption.setName("option");
        createOrderProductOption.setPrice(BigDecimal.valueOf(10));
        createOrderProductOption.setProductOptionId(UUID.randomUUID());
        createOrderProductOptions.add(createOrderProductOption);
        orderProduct.setOrderProductOptions(createOrderProductOptions);
        orderProducts.add(orderProduct);
        createOrder.setOrderProducts(orderProducts);

        OrderEntity savedOrderEntity = getSavedOrderEntity(createOrder);
        OrderProductEntity savedOrderProductEntity = getSavedOrderProductEntity(orderProduct, savedOrderEntity);
        OrderProductOptionEntity savedOrderProductOptionEntity = getOrderProductOptionEntity(createOrderProductOption, savedOrderProductEntity);
        savedOrderProductEntity.setOrderProductOptionEntities(Collections.singletonList(savedOrderProductOptionEntity));
        savedOrderEntity.setOrderProductEntities(Collections.singletonList(savedOrderProductEntity));

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrderEntity);
        when(orderProductRepository.save(any(OrderProductEntity.class))).thenReturn(savedOrderProductEntity);
        when(orderProductOptionRepository.saveAll(anyList())).thenReturn(Collections.singletonList(savedOrderProductOptionEntity));

        OrderDto result = orderDataAdapter.createOrder(createOrder);
        checkCreateOrder(createOrder, result);
    }

    @NotNull
    private OrderProductOptionEntity getOrderProductOptionEntity(CreateOrderProductOption createOrderProductOption, OrderProductEntity savedOrderProductEntity) {
        OrderProductOptionEntity savedOrderProductOptionEntity = new OrderProductOptionEntity();
        savedOrderProductOptionEntity.setId(UUID.randomUUID());
        savedOrderProductOptionEntity.setPrice(createOrderProductOption.getPrice());
        savedOrderProductOptionEntity.setProductOptionId(createOrderProductOption.getProductOptionId());
        savedOrderProductOptionEntity.setOrderProductEntity(savedOrderProductEntity);
        return savedOrderProductOptionEntity;
    }

    @NotNull
    private OrderProductEntity getSavedOrderProductEntity(CreateOrderProduct orderProduct, OrderEntity savedOrderEntity) {
        OrderProductEntity savedOrderProductEntity = new OrderProductEntity();
        savedOrderProductEntity.setId(UUID.randomUUID());
        savedOrderProductEntity.setPrice(orderProduct.getPrice());
        savedOrderProductEntity.setProductId(orderProduct.getProductId());
        savedOrderProductEntity.setOrderEntity(savedOrderEntity);
        return savedOrderProductEntity;
    }

    @NotNull
    private OrderEntity getSavedOrderEntity(CreateOrder createOrder) {
        OrderEntity savedOrderEntity = new OrderEntity();
        savedOrderEntity.setId(UUID.randomUUID());
        savedOrderEntity.setPrice(createOrder.getPrice());
        savedOrderEntity.setPriceType(createOrder.getPriceType());
        savedOrderEntity.setUserId(createOrder.getUserId());
        savedOrderEntity.setUserAddressId(createOrder.getUserAddressId());
        savedOrderEntity.setRestaurantFranchisingId(createOrder.getRestaurantFranchisingId());
        savedOrderEntity.setBasketId(createOrder.getBasketId());
        savedOrderEntity.setStatus(createOrder.getStatus());
        return savedOrderEntity;
    }

    private void checkCreateOrder(CreateOrder createOrder, OrderDto result) {
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(createOrder.getPrice(), result.getPrice());
        assertEquals(createOrder.getPriceType(), result.getPriceType());
        assertEquals(createOrder.getUserId(), result.getUserId());
        assertEquals(createOrder.getUserAddressId(), result.getUserAddressId());
        assertEquals(createOrder.getRestaurantFranchisingId(), result.getRestaurantFranchisingId());
        assertEquals(createOrder.getBasketId(), result.getBasketId());
        assertEquals(createOrder.getStatus(), result.getStatus());
        assertNotNull(result.getOrderProducts());
        assertEquals(1, result.getOrderProducts().size());
        assertEquals(new BigDecimal(50), result.getOrderProducts().get(0).getPrice());
        assertNotNull(result.getOrderProducts().get(0).getId());
        assertNotNull(result.getOrderProducts().get(0).getProductId());
        assertNotNull(result.getOrderProducts().get(0).getOrderProductOptions());
        assertEquals(1, result.getOrderProducts().get(0).getOrderProductOptions().size());
        assertEquals(new BigDecimal(10), result.getOrderProducts().get(0).getOrderProductOptions().get(0).getPrice());
        assertNotNull(result.getOrderProducts().get(0).getOrderProductOptions().get(0).getId());
        assertNotNull(result.getOrderProducts().get(0).getOrderProductOptions().get(0).getProductOptionId());
    }


    @Test
    public void testGetOrder_WhenOrderExist_ReturnOrderDto() {
        UUID orderId = UUID.randomUUID();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        OrderDto orderDto = orderDataAdapter.getOrder(orderId);
        assertEquals(orderId, orderDto.getId());
    }


    @Test
    public void testGetOrdersByPage_WhenOrdersExist_ReturnPageDtoWithOrders() {
        // Arrange
        UUID userId = UUID.randomUUID();
        GetOrdersByPage getOrdersByPage = GetOrdersByPage.builder().page(0).size(10).userId(userId).build();
        List<OrderEntity> orderEntities = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setUserId(userId);
        orderEntity.setStatus(OrderStatus.RECEIVED);
        orderEntities.add(orderEntity);
        Page<OrderEntity> orderEntityPage = new PageImpl<>(orderEntities);
        when(orderRepository.findAllByUserIdOrderByCreateDateDesc(userId, PageRequest.of(0, 10)))
                .thenReturn(orderEntityPage);

        PageDto<OrderDto> result = orderDataAdapter.getOrdersByPage(getOrdersByPage);

        assertEquals(1, result.getContents().size());
        assertEquals(orderEntity.getId(), result.getContents().get(0).getId());
        assertEquals(orderEntity.getUserId(), result.getContents().get(0).getUserId());
        assertEquals(orderEntity.getStatus(), result.getContents().get(0).getStatus());
    }

    @Test
    public void testUpdateOrderStatus_WhenOrderDoesNotExist_ThrowOrderApiException() {
        UUID orderId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PAYMENT_FAILED;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());


        assertThrows(OrderApiException.class, () -> orderDataAdapter.updateOrderStatus(orderId, orderStatus));
    }

    @Test
    public void testGetOrdersByStatusesAndRetryLessThanAndPage_ReturnPageDtoWithOrders() {
        List<OrderStatus> orderStatuses = List.of(OrderStatus.INITIAL);
        int maxRetryCount = 3;
        Pagination pagination = new Pagination(0, 10);
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Page<OrderEntity> pageOrderEntity = new PageImpl<>(List.of(new OrderEntity()));
        Mockito.when(orderRepository.findAllByStatusInAndRetryCountLessThanOrderByCreateDateDesc(orderStatuses,
                maxRetryCount, pageable)).thenReturn(pageOrderEntity);
        PageDto<OrderDto> pageDto = orderDataAdapter.getOrdersByStatusesAndRetryLessThanAndPage(orderStatuses,
                maxRetryCount, pagination);
        assertEquals(pageOrderEntity.getTotalElements(), Long.valueOf(pageDto.getTotalPage()));
        assertEquals(pageOrderEntity.getContent().size(), pageDto.getContents().size());
    }

    @Test
    public void testUpdateOrderStatus_WhenOrderExists_UpdateStatusAndReturnUpdatedOrder() {
        UUID orderId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PAYMENT_FAILED;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(OrderStatus.INITIAL);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        OrderDto result = orderDataAdapter.updateOrderStatus(orderId, orderStatus);

        assertEquals(orderId, result.getId());
        assertEquals(orderStatus, result.getStatus());
    }
    @Test
    public void testUpdateOrderStatusAndIncreaseRetryCount_WhenOrderExists_UpdateStatusAndIncreaseRetryCount() {
        UUID orderId = UUID.randomUUID();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setRetryCount(1);
        orderEntity.setStatus(OrderStatus.ROLLBACK);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        orderDataAdapter.updateOrderStatusAndIncreaseRetryCount(orderId, OrderStatus.PAYMENT_FAILED);
        Mockito.verify(orderRepository, Mockito.times(1)).save(orderEntity);
        assertEquals(OrderStatus.PAYMENT_FAILED, orderEntity.getStatus());
        assertEquals(2, orderEntity.getRetryCount());
    }
}