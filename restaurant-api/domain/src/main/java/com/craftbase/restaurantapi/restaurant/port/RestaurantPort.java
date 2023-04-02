package com.craftbase.restaurantapi.restaurant.port;

import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;

import java.util.UUID;

public interface RestaurantPort {
    RestaurantDto getRestaurantById(UUID id);

    boolean isRestaurantExistById(UUID id);

    UUID saveRestaurant(SaveRestaurant saveRestaurant);
}
