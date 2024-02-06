package com.example.orderapi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.orderapi.adapters.order.model.request.PlaceOrderRequest;
import com.example.orderapi.adapters.order.model.response.GetOrderResponse;
import com.example.orderapi.adapters.order.model.response.OrderProductOptionResponse;
import com.example.orderapi.adapters.order.model.response.OrderProductResponse;
import com.example.orderapi.adapters.order.model.response.OrderResponse;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.payment.model.dto.CreditCard;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"listeners=PLAINTEXT://localhost:29092", "port=29092"},
        topics = "order-rollback")
class OrderControllerTest extends BaseMySQLContainerTest {
    @Test
    void testPlaceOrder() {
        PlaceOrderRequest placeOrderRequest = PlaceOrderRequest.builder()
                .userAddressId(UUID.fromString("1c73c532-91df-4a41-a176-b44ced6fbd14"))
                .restaurantFranchisingId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .userId(UUID.fromString("f3d5470b-0f27-48f8-a34b-1555eea82273"))
                .card(CreditCard.builder()
                        .cardHolderName("Test Test")
                        .cardNumber("4256690000000001")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();
        ResponseEntity<UUID> response =
                testRestTemplate.postForEntity(
                        "/v1/orders/place-order", placeOrderRequest, UUID.class);
        UUID orderId = response.getBody();
        assertNotNull(orderId);
    }

    @Test
    void testGetOrders() {

        UUID userId = UUID.fromString("f3d5470b-0f27-48f8-a34b-1555eea82273");
        Integer page = 0;
        Integer size = 10;

        // When
        ResponseEntity<GetOrderResponse> response = testRestTemplate.exchange("/v1/orders/histories/{userId}?page={page}&size={size}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                userId,
                page,
                size);
        GetOrderResponse actualResponse = response.getBody();
        GetOrderResponse expectedResponse = GetOrderResponse.builder()
                .orders(List.of(OrderResponse.builder()
                        .price(new BigDecimal("330.00"))
                        .userId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                        .userAddressId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                        .restaurantFranchisingId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                        .basketId(UUID.fromString("f3d5470b-0f27-48f8-a34b-1555eea82271"))
                        .orderProducts(List.of(
                                OrderProductResponse.builder()
                                        .price(new BigDecimal("100.00"))
                                        .productId(UUID.fromString("6222199b-31d1-4c36-94fb-5826d57def6b"))
                                        .name("Product")
                                        .orderProductOptions(List.of(
                                                OrderProductOptionResponse.builder()
                                                        .price(new BigDecimal("10.00"))
                                                        .productOptionId(UUID.fromString("e29337ac-7623-4bd7-992b-bab7c121414a"))
                                                        .name("extra öz")
                                                        .build()))
                                        .build()))
                        .status(OrderStatus.RECEIVED)
                        .build()))
                .page(page)
                .size(size)
                .totalPage(1)
                .build();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getPage(), actualResponse.getPage());
        assertEquals(expectedResponse.getSize(), actualResponse.getSize());
        assertEquals(expectedResponse.getTotalPage(), actualResponse.getTotalPage());

        for (int i = 0; i < expectedResponse.getOrders().size(); i++) {
            OrderResponse expectedOrder = expectedResponse.getOrders().get(i);
            OrderResponse actualOrder = expectedResponse.getOrders().get(i);

            assertEquals(expectedOrder.getPrice(), actualOrder.getPrice());
            assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
            assertEquals(expectedOrder.getUserAddressId(), actualOrder.getUserAddressId());
            assertEquals(expectedOrder.getRestaurantFranchisingId(), actualOrder.getRestaurantFranchisingId());
            assertEquals(expectedOrder.getBasketId(), actualOrder.getBasketId());
            assertEquals(expectedOrder.getOrderProducts().size(), actualOrder.getOrderProducts().size());

            for (int j = 0; j < expectedOrder.getOrderProducts().size(); j++) {
                OrderProductResponse expectedProduct = expectedOrder.getOrderProducts().get(i);
                OrderProductResponse actualProduct = actualOrder.getOrderProducts().get(i);

                assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
                assertEquals(expectedProduct.getProductId(), actualProduct.getProductId());
                assertEquals(expectedProduct.getName(), actualProduct.getName());
                assertEquals(expectedProduct.getOrderProductOptions().size(), actualProduct.getOrderProductOptions().size());

                for (int k = 0; k < expectedProduct.getOrderProductOptions().size(); k++) {
                    OrderProductOptionResponse expectedOption = expectedProduct.getOrderProductOptions().get(j);
                    OrderProductOptionResponse actualOption = actualProduct.getOrderProductOptions().get(j);

                    assertEquals(expectedOption.getPrice(), actualOption.getPrice());
                    assertEquals(expectedOption.getProductOptionId(), actualOption.getProductOptionId());
                    assertEquals(expectedOption.getName(), actualOption.getName());
                }
                assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());

            }
        }
    }
}
