package com.craftbase.orderapi.order;

import com.craftbase.orderapi.adapters.OrderFakeAdapter;
import com.craftbase.orderapi.adapters.PaymentFakeAdapter;
import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.order.usecase.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderRollbackVoidUseCaseHandlerTest {
    private final OrderFakeAdapter orderFakeAdapter = new OrderFakeAdapter();
    private final OrderRollbackVoidUseCaseHandler handler =
            new OrderRollbackVoidUseCaseHandler(orderFakeAdapter,
                    new PaymentFakeAdapter()
            );
    @Test
    void testOrderRollback_WhenOrderRefunded_ReturnNothing() {
        CreateOrder createOrder = getCreateOrder();
        orderFakeAdapter.createOrder(createOrder);
        GetOrdersByPage getOrdersByPage = GetOrdersByPage.builder().page(0).size(10).userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892")).build();

        PageDto<OrderDto> ordersByPage = orderFakeAdapter.getOrdersByPage(getOrdersByPage);
        UUID orderId = ordersByPage.getContents().get(0).getId();
        OrderRollback orderRollback = OrderRollback.builder().orderId(orderId).build();

        handler.handle(orderRollback);
        PageDto<OrderDto> ordersByPage1 = orderFakeAdapter.getOrdersByPage(getOrdersByPage);

        assertEquals(OrderStatus.ROLLBACK, ordersByPage1.getContents().get(0).getStatus());
    }


    private CreateOrder getCreateOrder() {
        CreateOrder createOrder = new CreateOrder();
        createOrder.setPrice(BigDecimal.valueOf(60));
        createOrder.setPriceType(PriceType.TRY);
        createOrder.setUserId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"));
        createOrder.setUserAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"));
        createOrder.setRestaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"));
        createOrder.setBasketId(UUID.fromString("b4a1e188-99b2-4768-b375-a7a7259c94ab"));
        createOrder.setStatus(OrderStatus.RECEIVED);

        CreateOrderProduct orderProduct = new CreateOrderProduct();
        orderProduct.setName("Cheeseburger");
        orderProduct.setPrice(BigDecimal.valueOf(50));
        orderProduct.setProductId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"));
        List<CreateOrderProductOption> createOrderProductOptions = new ArrayList<>();
        CreateOrderProductOption createOrderProductOption = new CreateOrderProductOption();
        createOrderProductOption.setName("Extra Cheese");
        createOrderProductOption.setPrice(BigDecimal.valueOf(10));
        createOrderProductOption.setProductOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"));
        createOrderProductOptions.add(createOrderProductOption);
        orderProduct.setOrderProductOptions(createOrderProductOptions);
        List<CreateOrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);
        createOrder.setOrderProducts(orderProducts);
        return createOrder;
    }
}