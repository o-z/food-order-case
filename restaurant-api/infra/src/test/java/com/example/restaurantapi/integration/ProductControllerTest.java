package com.example.restaurantapi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.example.restaurantapi.adapters.product.model.request.SaveProductOptionRequest;
import com.example.restaurantapi.adapters.product.model.request.SaveProductRequest;
import com.example.restaurantapi.adapters.product.model.response.ProductOptionResponse;
import com.example.restaurantapi.adapters.product.model.response.ProductResponse;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


class ProductControllerTest extends BaseMySQLContainerTest {

    private final String BASE_URL = "/v1/products";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Test
    void testGetProductById() {

        ProductResponse expectedProductResponse = ProductResponse.builder()
                .id(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"))
                .createDate(LocalDateTime.parse("2023-04-01 16:37:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updateDate(LocalDateTime.parse("2023-04-01 16:37:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .categoryId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .desc("Big Mac Moda")
                .imageUrl("Big Mac Moda")
                .name("Big Mac Moda")
                .price(new BigDecimal("110.00"))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                .status(ProductStatus.ACTIVE)
                .productOptionMap(Map.of(
                        UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"),
                        ProductOptionResponse.builder()
                                .id(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"))
                                .createDate(LocalDateTime.parse("2023-04-01 16:38:37", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .updateDate(LocalDateTime.parse("2023-04-01 16:38:37", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .desc("Exstra Pickle")
                                .name("Exstra Pickle")
                                .price(new BigDecimal("10.00"))
                                .priceType(PriceType.TRY)
                                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                                .status(ProductStatus.ACTIVE)
                                .build()
                ))
                .build();


        ResponseEntity<ProductResponse> response = testRestTemplate.getForEntity(BASE_URL + "/" + expectedProductResponse.getId(), ProductResponse.class);
        ProductResponse actualProductResponse = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProductResponse.getId(), actualProductResponse.getId());
        assertEquals(expectedProductResponse.getCreateDate(), actualProductResponse.getCreateDate());
        assertEquals(expectedProductResponse.getUpdateDate(), actualProductResponse.getUpdateDate());
        assertEquals(expectedProductResponse.getCategoryId(), actualProductResponse.getCategoryId());
        assertEquals(expectedProductResponse.getDesc(), actualProductResponse.getDesc());
        assertEquals(expectedProductResponse.getImageUrl(), actualProductResponse.getImageUrl());
        assertEquals(expectedProductResponse.getName(), actualProductResponse.getName());
        assertEquals(expectedProductResponse.getPrice(), actualProductResponse.getPrice());
        assertEquals(expectedProductResponse.getPriceType(), actualProductResponse.getPriceType());
        assertEquals(expectedProductResponse.getRestaurantFranchisingId(), actualProductResponse.getRestaurantFranchisingId());
        assertEquals(expectedProductResponse.getStatus(), actualProductResponse.getStatus());
        assertEquals(expectedProductResponse.getProductOptionMap().get(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07")),
                actualProductResponse.getProductOptionMap().get(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07")));

    }

    @Test
    void testGetProductByIdSet() {


        Map<UUID, ProductResponse> expectedProductResponseMap = Map.of(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"), ProductResponse.builder()
                .id(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"))
                .createDate(LocalDateTime.parse("2023-04-01 16:37:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updateDate(LocalDateTime.parse("2023-04-01 16:37:57", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .categoryId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .desc("Big Mac Moda")
                .imageUrl("Big Mac Moda")
                .name("Big Mac Moda")
                .price(new BigDecimal("110.00"))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                .status(ProductStatus.ACTIVE)
                .productOptionMap(Map.of(
                        UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"),
                        ProductOptionResponse.builder()
                                .id(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07"))
                                .createDate(LocalDateTime.parse("2023-04-01 16:38:37", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .updateDate(LocalDateTime.parse("2023-04-01 16:38:37", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .desc("Exstra Pickle")
                                .name("Exstra Pickle")
                                .price(new BigDecimal("10.00"))
                                .priceType(PriceType.TRY)
                                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                                .status(ProductStatus.ACTIVE)
                                .build()
                ))
                .build());
        Set<UUID> productIdSet = Set.of(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"));

        ResponseEntity<Map<UUID, ProductResponse>> response = testRestTemplate.exchange(
                BASE_URL + "?uuids=" + productIdSet.stream().map(UUID::toString).collect(Collectors.joining(",")),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        Map<UUID, ProductResponse> actualProductResponseMap = response.getBody();
        ProductResponse expectedProductResponse = expectedProductResponseMap.get(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"));
        ProductResponse actualProductResponse = actualProductResponseMap.get(UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, actualProductResponseMap.size());
        assertEquals(expectedProductResponse.getId(), actualProductResponse.getId());
        assertEquals(expectedProductResponse.getCreateDate(), actualProductResponse.getCreateDate());
        assertEquals(expectedProductResponse.getUpdateDate(), actualProductResponse.getUpdateDate());
        assertEquals(expectedProductResponse.getCategoryId(), actualProductResponse.getCategoryId());
        assertEquals(expectedProductResponse.getDesc(), actualProductResponse.getDesc());
        assertEquals(expectedProductResponse.getImageUrl(), actualProductResponse.getImageUrl());
        assertEquals(expectedProductResponse.getName(), actualProductResponse.getName());
        assertEquals(expectedProductResponse.getPrice(), actualProductResponse.getPrice());
        assertEquals(expectedProductResponse.getPriceType(), actualProductResponse.getPriceType());
        assertEquals(expectedProductResponse.getRestaurantFranchisingId(), actualProductResponse.getRestaurantFranchisingId());
        assertEquals(expectedProductResponse.getStatus(), actualProductResponse.getStatus());
        assertEquals(expectedProductResponse.getProductOptionMap().get(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07")),
                actualProductResponse.getProductOptionMap().get(UUID.fromString("915a606b-ae98-4cd6-963e-6cccda571f07")));
    }

    @Test
    void saveProduct_returnsCreatedStatus() {
        SaveProductRequest request = SaveProductRequest.builder()
                .name("Test Product")
                .desc("Test Product Description")
                .imageUrl("https://example.com/test-product.jpg")
                .price(BigDecimal.valueOf(9.99))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                .categoryId(UUID.randomUUID())
                .baseProductId(UUID.fromString("3ce8e42b-00cf-46b4-86f9-e969f106d39e"))
                .status(ProductStatus.ACTIVE)
                .build();

        ResponseEntity<UUID> response = testRestTemplate.postForEntity(BASE_URL, request, UUID.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Optional<ProductEntity> savedProduct = productRepository.findById(response.getBody());
        ProductEntity productEntity = savedProduct.get();
        assertTrue(savedProduct.isPresent());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request.getCategoryId(), productEntity.getCategoryId());
        assertEquals(request.getDesc(), productEntity.getDesc());
        assertEquals(request.getImageUrl(), productEntity.getImageUrl());
        assertEquals(request.getName(), productEntity.getName());
        assertEquals(request.getPrice(), productEntity.getPrice());
        assertEquals(request.getPriceType(), productEntity.getPriceType());
        assertEquals(request.getRestaurantFranchisingId(), productEntity.getRestaurantFranchisingId());
        assertEquals(request.getStatus(), productEntity.getStatus());
    }

    @Test
    void saveProductOption_returnsCreatedStatus() {
        UUID productId = UUID.fromString("66d551b7-2612-4bda-96e3-7cd082937465");

        SaveProductOptionRequest request = SaveProductOptionRequest.builder()
                .name("Test Option")
                .desc("Test Option Description")
                .price(new BigDecimal("1.99"))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                .baseProductOptionId(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .status(ProductStatus.ACTIVE)
                .build();

        ResponseEntity<UUID> response = testRestTemplate.postForEntity(BASE_URL + "/" + productId + "/options", request, UUID.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Optional<ProductOptionEntity> savedProductOptionEntity = productOptionRepository.findById(response.getBody());

        assertTrue(savedProductOptionEntity.isPresent());
        assertEquals(request.getDesc(), savedProductOptionEntity.get().getDesc());
        assertEquals(request.getName(), savedProductOptionEntity.get().getName());
        assertEquals(request.getPrice(), savedProductOptionEntity.get().getPrice());
        assertEquals(request.getPriceType(), savedProductOptionEntity.get().getPriceType());
        assertEquals(request.getRestaurantFranchisingId(), savedProductOptionEntity.get().getRestaurantFranchisingId());
        assertEquals(request.getStatus(), savedProductOptionEntity.get().getStatus());
    }

}
