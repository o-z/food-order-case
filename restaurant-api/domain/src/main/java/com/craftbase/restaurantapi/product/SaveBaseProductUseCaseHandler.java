package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.port.BaseProductPort;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProduct;
import com.craftbase.restaurantapi.restaurant.port.RestaurantPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveBaseProductUseCaseHandler implements UseCaseHandler<UUID, SaveBaseProduct> {
    private final BaseProductPort baseProductPort;
    private final RestaurantPort restaurantPort;

    @Override
    public UUID handle(SaveBaseProduct useCase) {
        boolean isRestaurantExistById = restaurantPort.isRestaurantExistById(useCase.getRestaurantId());
        if (!isRestaurantExistById) {
            throw new RestaurantApiException(ErrorCode.RESTAURANT_NOT_FOUND_ERROR);
        }
        return baseProductPort.saveBaseProduct(useCase);
    }
}
