package com.example.restaurantapi.restaurant.port;

import com.example.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import java.util.UUID;

public interface RestaurantFranchisingPort {
    UUID saveRestaurantFranchising(SaveRestaurantFranchising saveRestaurantFranchising);

    RestaurantFranchisingDto getRestaurantFranchisingById(UUID restaurantFranchisingId);

    boolean isRestaurantFranchisingExistById(UUID restaurantFranchisingId);


}
