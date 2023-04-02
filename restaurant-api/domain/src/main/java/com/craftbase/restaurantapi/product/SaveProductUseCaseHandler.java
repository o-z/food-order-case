package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.port.BaseProductPort;
import com.craftbase.restaurantapi.product.port.ProductPort;
import com.craftbase.restaurantapi.product.usecase.SaveProduct;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.craftbase.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveProductUseCaseHandler implements UseCaseHandler<UUID, SaveProduct> {

    private final ProductPort productPort;
    private final BaseProductPort baseProductPort;
    private final RestaurantFranchisingPort restaurantFranchisingPort;

    @Override
    public UUID handle(SaveProduct useCase) {
        RestaurantFranchisingDto restaurantFranchisingDto = restaurantFranchisingPort
                .getRestaurantFranchisingById(useCase.getRestaurantFranchisingId());
        BaseProductDto baseProductDto = baseProductPort.getBaseProductById(useCase.getBaseProductId());

        if (!restaurantFranchisingDto.getRestaurantId().equals(baseProductDto.getRestaurantId())) {
            throw new RestaurantApiException(ErrorCode.RESTAURANT_ID_NOT_EQUAL_ERROR);
        }
        if (useCase.getPrice() == null) {
            useCase.setPrice(baseProductDto.getDefaultPrice());
        }
        return productPort.saveProduct(useCase);
    }
}
