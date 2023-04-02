package com.craftbase.restaurantapi.restaurant;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.restaurant.port.RestaurantPort;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveRestaurantUseCaseHandler implements UseCaseHandler<UUID, SaveRestaurant> {

    private final RestaurantPort restaurantPort;

    @Override
    public UUID handle(SaveRestaurant useCase) {
        return restaurantPort.saveRestaurant(useCase);
    }
}
