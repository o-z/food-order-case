package com.example.restaurantapi.adapters;

import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.example.restaurantapi.restaurant.port.RestaurantPort;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestaurantFakeAdapter implements RestaurantPort {

    private final Map<UUID, RestaurantDto> restaurantMap = new HashMap<>();

    @Override
    public RestaurantDto getRestaurantById(UUID id) {
        return restaurantMap.get(id);
    }

    @Override
    public boolean isRestaurantExistById(UUID id) {
        return restaurantMap.containsKey(id);
    }

    @Override
    public UUID saveRestaurant(SaveRestaurant saveRestaurant) {
        UUID id = UUID.randomUUID();
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(id)
                .name(saveRestaurant.getName())
                .desc(saveRestaurant.getDesc())
                .imageUrl(saveRestaurant.getImageUrl())
                .status(RestaurantStatus.ACTIVE)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        restaurantMap.put(id, restaurantDto);
        return id;
    }

}
