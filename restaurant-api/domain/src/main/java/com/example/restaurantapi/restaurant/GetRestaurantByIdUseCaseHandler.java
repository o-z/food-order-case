package com.example.restaurantapi.restaurant;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.port.RestaurantPort;
import com.example.restaurantapi.restaurant.usecase.GetRestaurantById;
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
