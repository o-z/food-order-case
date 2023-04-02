package com.craftbase.restaurantapi.restaurant;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveRestaurantFranchisingUseCaseHandler implements UseCaseHandler<UUID, SaveRestaurantFranchising> {
    private final RestaurantFranchisingPort restaurantPort;

    @Override
    public UUID handle(SaveRestaurantFranchising useCase) {
        return restaurantPort.saveRestaurantFranchising(useCase);
    }
}
