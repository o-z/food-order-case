package com.example.restaurantapi.product;

import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.model.dto.BaseProductDto;
import com.example.restaurantapi.product.port.BaseProductPort;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.SaveProduct;
import com.example.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.example.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

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
