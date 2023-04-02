package com.craftbase.restaurantapi.restaurant.port;

import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;

import java.util.UUID;

public interface RestaurantFranchisingPort {
    UUID saveRestaurantFranchising(SaveRestaurantFranchising saveRestaurantFranchising);

    RestaurantFranchisingDto getRestaurantFranchisingById(UUID restaurantFranchisingId);

    boolean isRestaurantFranchisingExistById(UUID restaurantFranchisingId);


}
