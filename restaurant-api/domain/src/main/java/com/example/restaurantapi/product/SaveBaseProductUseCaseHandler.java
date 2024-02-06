package com.example.restaurantapi.product;

import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.port.BaseProductPort;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;
import com.example.restaurantapi.restaurant.port.RestaurantPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

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
