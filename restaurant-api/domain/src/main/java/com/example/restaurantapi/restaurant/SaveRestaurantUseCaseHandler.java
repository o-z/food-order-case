package com.example.restaurantapi.restaurant;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.restaurant.port.RestaurantPort;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class SaveRestaurantUseCaseHandler implements UseCaseHandler<UUID, SaveRestaurant> {

    private final RestaurantPort restaurantPort;

    @Override
    public UUID handle(SaveRestaurant useCase) {
        return restaurantPort.saveRestaurant(useCase);
    }
}
