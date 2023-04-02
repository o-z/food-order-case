package com.craftbase.restaurantapi.integration;

import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantFranchisingEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantFranchisingRepository;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.craftbase.restaurantapi.adapters.restaurant.model.request.SaveRestaurantFranchisingRequest;
import com.craftbase.restaurantapi.adapters.restaurant.model.request.SaveRestaurantRequest;
import com.craftbase.restaurantapi.adapters.restaurant.model.response.RestaurantFranchisingResponse;
import com.craftbase.restaurantapi.adapters.restaurant.model.response.RestaurantResponse;
import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class RestaurantControllerTest extends BaseMySQLContainerTest {

    private final String BASE_URL = "/v1/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantFranchisingRepository restaurantFranchisingRepository;

    @Test
    void getRestaurantById() {
        UUID restaurantId = UUID.fromString("0ec13e48-4c4e-479c-bb73-de8b1a557c97");
        ResponseEntity<RestaurantResponse> getRestaurantByIdResponse = testRestTemplate.getForEntity(BASE_URL + "/" + restaurantId, RestaurantResponse.class);
        RestaurantResponse expectedResponse = getRestaurantByIdResponse.getBody();
        assertEquals(restaurantId, expectedResponse.getId());

        RestaurantResponse actualResponse = RestaurantResponse.builder()
                .id(restaurantId)
                .createDate(LocalDateTime.parse("2023-04-01 16:36:23", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updateDate(LocalDateTime.parse("2023-04-01 16:36:23", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .name("Mc Donalds")
                .desc("Mc Donalds")
                .imageUrl("Mc Donalds Url")
                .status(RestaurantStatus.ACTIVE)
                .restaurantFranchisings(List.of(
                        RestaurantFranchisingResponse.builder()
                                .id(UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60"))
                                .createDate(LocalDateTime.parse("2023-04-01 16:36:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .updateDate(LocalDateTime.parse("2023-04-01 16:36:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .desc("Mc Donalds Moda")
                                .name("Mc Donalds Moda")
                                .status(RestaurantStatus.ACTIVE)
                                .address("Moda")
                                .country("Turkey")
                                .build()
                ))
                .build();
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getCreateDate(), actualResponse.getCreateDate());
        assertEquals(expectedResponse.getUpdateDate(), actualResponse.getUpdateDate());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getDesc(), actualResponse.getDesc());
        assertEquals(expectedResponse.getImageUrl(), actualResponse.getImageUrl());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getRestaurantFranchisings().stream()
                        .filter(restaurantFranchisingResponse -> UUID.fromString("5ebf353f-71df-4e60-b3ae-b2e75b924f60")
                                .equals(restaurantFranchisingResponse.getId())).findFirst().get(),
                actualResponse.getRestaurantFranchisings().get(0));
    }

    @Test
    void saveRestaurant() {
        SaveRestaurantRequest saveRestaurantRequest = SaveRestaurantRequest.builder()
                .name("Test Restaurant")
                .desc("Test Restaurant Description")
                .imageUrl("https://example.com/image.png")
                .status(RestaurantStatus.ACTIVE)
                .build();
        ResponseEntity<UUID> saveRestaurantResponse = testRestTemplate.postForEntity(BASE_URL, saveRestaurantRequest, UUID.class);
        UUID savedRestaurantId = saveRestaurantResponse.getBody();
        RestaurantEntity restaurantEntity = restaurantRepository.findById(savedRestaurantId).get();


        assertEquals(savedRestaurantId, restaurantEntity.getId());
        assertEquals(saveRestaurantRequest.getName(), restaurantEntity.getName());
        assertEquals(saveRestaurantRequest.getDesc(), restaurantEntity.getDesc());
        assertEquals(saveRestaurantRequest.getImageUrl(), restaurantEntity.getImageUrl());
        assertEquals(RestaurantStatus.ACTIVE, restaurantEntity.getStatus());
    }

    @Test
    void saveRestaurantFranchising() {
        UUID restaurantId = UUID.fromString("0ec13e48-4c4e-479c-bb73-de8b1a557c97");

        SaveRestaurantFranchisingRequest saveRestaurantFranchisingRequest = SaveRestaurantFranchisingRequest.builder()
                .name("Test Restaurant")
                .desc("Test Restaurant Description")
                .address("Test Restaurant Address")
                .country("Test Restaurant Country")
                .status(RestaurantStatus.ACTIVE)
                .build();
        ResponseEntity<UUID> saveRestaurantFranchisingResponse = testRestTemplate.postForEntity(BASE_URL + "/" + restaurantId + "/" + "franchisings", saveRestaurantFranchisingRequest, UUID.class);
        UUID savedRestaurantFranchisingId = saveRestaurantFranchisingResponse.getBody();
        RestaurantFranchisingEntity restaurantFranchisingEntity = restaurantFranchisingRepository.findById(savedRestaurantFranchisingId).get();


        assertEquals(savedRestaurantFranchisingId, restaurantFranchisingEntity.getId());
        assertEquals(saveRestaurantFranchisingRequest.getName(), restaurantFranchisingEntity.getName());
        assertEquals(saveRestaurantFranchisingRequest.getDesc(), restaurantFranchisingEntity.getDesc());
        assertEquals(saveRestaurantFranchisingRequest.getAddress(), restaurantFranchisingEntity.getAddress());
        assertEquals(saveRestaurantFranchisingRequest.getCountry(), restaurantFranchisingEntity.getCountry());
        assertEquals(RestaurantStatus.ACTIVE, restaurantFranchisingEntity.getStatus());

    }
}