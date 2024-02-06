package com.example.restaurantapi.restaurant.port;

import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import java.util.UUID;

public interface RestaurantPort {
    RestaurantDto getRestaurantById(UUID id);

    boolean isRestaurantExistById(UUID id);

    UUID saveRestaurant(SaveRestaurant saveRestaurant);
}
