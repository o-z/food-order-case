package com.craftbase.restaurantapi.integration;

import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.craftbase.restaurantapi.adapters.product.model.request.SaveBaseProductOptionRequest;
import com.craftbase.restaurantapi.adapters.product.model.request.SaveBaseProductRequest;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BaseProductControllerTest extends BaseMySQLContainerTest {

    private final String BASE_URL = "/v1/base-products";

    @Autowired
    private BaseProductRepository baseProductRepository;

    @Autowired
    private BaseProductOptionRepository baseProductOptionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveBaseProduct() throws JsonProcessingException {
        SaveBaseProductRequest request = SaveBaseProductRequest.builder()
                .name("Test Product")
                .desc("Test Description")
                .imageUrl("http://test.com/image.jpg")
                .defaultPrice(new BigDecimal("10.00"))
                .defaultPriceType(PriceType.TRY)
                .categoryId(UUID.randomUUID())
                .restaurantId(UUID.fromString("0ec13e48-4c4e-479c-bb73-de8b1a557c97"))
                .status(ProductStatus.ACTIVE)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        ResponseEntity<UUID> responseEntity = testRestTemplate.postForEntity(BASE_URL, entity, UUID.class);

        Optional<BaseProductEntity> actualBaseProductEntityOptional = baseProductRepository.findById(responseEntity.getBody());
        BaseProductEntity actualBaseProductEntity = actualBaseProductEntityOptional.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(request.getCategoryId(), actualBaseProductEntity.getCategoryId());
        assertEquals(request.getDesc(), actualBaseProductEntity.getDesc());
        assertEquals(request.getImageUrl(), actualBaseProductEntity.getImageUrl());
        assertEquals(request.getName(), actualBaseProductEntity.getName());
        assertEquals(request.getDefaultPrice(), actualBaseProductEntity.getDefaultPrice());
        assertEquals(request.getDefaultPriceType(), actualBaseProductEntity.getDefaultPriceType());
        assertEquals(request.getRestaurantId(), actualBaseProductEntity.getRestaurantId());
        assertEquals(request.getStatus(), actualBaseProductEntity.getStatus());
    }

    @Test
    void testSaveBaseProductOption() throws JsonProcessingException {
        UUID baseProductId = UUID.fromString("3ce8e42b-00cf-46b4-86f9-e969f106d39e");
        SaveBaseProductOptionRequest request = SaveBaseProductOptionRequest.builder()
                .name("Test Option")
                .desc("Test Option Description")
                .defaultPrice(new BigDecimal("5.00"))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        ResponseEntity<UUID> responseEntity = testRestTemplate.postForEntity(BASE_URL + "/" + baseProductId + "/base-options", entity, UUID.class);

        Optional<BaseProductOptionEntity> actualBaseProductOptionEntityOptional = baseProductOptionRepository.findById(responseEntity.getBody());
        BaseProductOptionEntity actualBaseProductOptionEntity = actualBaseProductOptionEntityOptional.get();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(request.getDesc(), actualBaseProductOptionEntity.getDesc());
        assertEquals(request.getName(), actualBaseProductOptionEntity.getName());
        assertEquals(request.getDefaultPrice(), actualBaseProductOptionEntity.getDefaultPrice());
        assertEquals(request.getDefaultPriceType(), actualBaseProductOptionEntity.getDefaultPriceType());
        assertEquals(request.getDefaultPriceType(), actualBaseProductOptionEntity.getDefaultPriceType());
        assertEquals(baseProductId, actualBaseProductOptionEntity.getBaseProductEntity().getId());
        assertEquals(request.getStatus(), actualBaseProductOptionEntity.getStatus());
    }

}