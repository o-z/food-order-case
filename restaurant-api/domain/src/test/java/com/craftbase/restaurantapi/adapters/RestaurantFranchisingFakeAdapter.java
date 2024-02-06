package com.example.restaurantapi.adapters;

import com.example.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.example.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestaurantFranchisingFakeAdapter implements RestaurantFranchisingPort {

    private final Map<UUID, RestaurantFranchisingDto> restaurantFranchisingMap = new HashMap<>();

    @Override
    public UUID saveRestaurantFranchising(SaveRestaurantFranchising saveRestaurantFranchising) {
        UUID restaurantFranchisingId = UUID.randomUUID();
        RestaurantFranchisingDto restaurantFranchisingDto = RestaurantFranchisingDto.builder()
                .id(restaurantFranchisingId)
                .name(saveRestaurantFranchising.getName())
                .address(saveRestaurantFranchising.getAddress())
                .restaurantId(saveRestaurantFranchising.getRestaurantId())
                .country(saveRestaurantFranchising.getCountry())
                .build();
        restaurantFranchisingMap.put(restaurantFranchisingId, restaurantFranchisingDto);
        return restaurantFranchisingId;
    }

    @Override
    public RestaurantFranchisingDto getRestaurantFranchisingById(UUID restaurantFranchisingId) {
        return restaurantFranchisingMap.get(restaurantFranchisingId);
    }

    @Override
    public boolean isRestaurantFranchisingExistById(UUID restaurantFranchisingId) {
        return restaurantFranchisingMap.containsKey(restaurantFranchisingId);
    }
}
