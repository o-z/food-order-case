package com.example.orderapi.integration;

import com.example.orderapi.adapters.basket.model.request.AddProductToBasketRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


class BasketControllerTest extends BaseMySQLContainerTest {

    @Test
    void testAddProductToBasket() {

        AddProductToBasketRequest request = AddProductToBasketRequest.builder()
                .productId(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"))
                .productOptionIds(List.of(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07")))
                .build();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AddProductToBasketRequest> entity = new HttpEntity<>(request, headers);
        UUID userId = UUID.fromString("2f2d7c22-30eb-4834-8162-22c015c74ded");

        testRestTemplate.exchange("/v1/baskets/{userId}/add-product",
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                },
                userId);
    }

}
