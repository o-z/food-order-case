package com.example.restaurantapi.restaurant;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class SaveRestaurantFranchisingUseCaseHandler implements UseCaseHandler<UUID, SaveRestaurantFranchising> {
    private final RestaurantFranchisingPort restaurantPort;

    @Override
    public UUID handle(SaveRestaurantFranchising useCase) {
        return restaurantPort.saveRestaurantFranchising(useCase);
    }
}
