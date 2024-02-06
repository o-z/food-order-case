package com.example.orderapi.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.orderapi.adapters.OrderFakeAdapter;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.dto.OrderProductDto;
import com.example.orderapi.order.model.dto.OrderProductOptionDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.order.usecase.CreateOrder;
import com.example.orderapi.order.usecase.CreateOrderProduct;
import com.example.orderapi.order.usecase.CreateOrderProductOption;
import com.example.orderapi.order.usecase.GetOrdersByPage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

public class GetOrdersByPageUseCaseHandlerTest {
    private final OrderFakeAdapter orderFakeAdapter = new OrderFakeAdapter();
    private final GetOrdersByPageUseCaseHandler handler = new GetOrdersByPageUseCaseHandler(orderFakeAdapter);


    @Test
    public void testGetOrdersByPage_WhenOrderExists_ReturnOrdersPage () {

        GetOrdersByPage getOrdersByPage = GetOrdersByPage.builder().page(0).size(10).userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892")).build();
        CreateOrder createOrder = getCreateOrder();
        orderFakeAdapter.createOrder(createOrder);
        handler.handle(getOrdersByPage);

        OrderDto actualOrder = orderFakeAdapter.getOrdersByPage(getOrdersByPage).getContents().get(0);

        OrderDto expectedOrderDto = getExpectedOrderDto();
        assertEquals(expectedOrderDto.getPrice(), actualOrder.getPrice());
        assertEquals(expectedOrderDto.getPriceType(), actualOrder.getPriceType());
        assertEquals(expectedOrderDto.getUserId(), actualOrder.getUserId());
        assertEquals(expectedOrderDto.getUserAddressId(), actualOrder.getUserAddressId());
        assertEquals(expectedOrderDto.getRestaurantFranchisingId(), actualOrder.getRestaurantFranchisingId());
        assertEquals(expectedOrderDto.getBasketId(), actualOrder.getBasketId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getPrice(), actualOrder.getOrderProducts().get(0).getPrice());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getProductId(), actualOrder.getOrderProducts().get(0).getProductId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getName(), actualOrder.getOrderProducts().get(0).getName());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getPrice(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getPrice());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getProductOptionId(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getProductOptionId());
        assertEquals(expectedOrderDto.getOrderProducts().get(0).getOrderProductOptions().get(0).getName(), actualOrder.getOrderProducts().get(0).getOrderProductOptions().get(0).getName());
        assertEquals(expectedOrderDto.getStatus(), actualOrder.getStatus());

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

    private OrderDto getExpectedOrderDto() {
        return OrderDto.builder()
                .id(UUID.fromString("97bae7d8-c7e9-4bbe-aa25-70b01cbc919e"))
                .price(new BigDecimal("60"))
                .priceType(PriceType.TRY)
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .basketId(UUID.fromString("b4a1e188-99b2-4768-b375-a7a7259c94ab"))
                .orderProducts(Collections.singletonList(
                        OrderProductDto.builder()
                                .price(new BigDecimal("50"))
                                .productId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                                .name("Cheeseburger")
                                .orderProductOptions(Collections.singletonList(
                                        OrderProductOptionDto.builder()
                                                .price(new BigDecimal("10"))
                                                .productOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"))
                                                .name("Extra Cheese")
                                                .build()
                                ))
                                .build()
                ))
                .status(OrderStatus.RECEIVED)
                .build();
    }
}