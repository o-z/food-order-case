package com.craftbase.restaurantapi.restaurant;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.craftbase.restaurantapi.restaurant.port.RestaurantPort;
import com.craftbase.restaurantapi.restaurant.usecase.GetRestaurantById;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetRestaurantByIdUseCaseHandler implements UseCaseHandler<RestaurantDto, GetRestaurantById> {
    private final RestaurantPort restaurantPort;

    @Override
    public RestaurantDto handle(GetRestaurantById useCase) {
        return restaurantPort.getRestaurantById(useCase.getRestaurantId());
    }
}
