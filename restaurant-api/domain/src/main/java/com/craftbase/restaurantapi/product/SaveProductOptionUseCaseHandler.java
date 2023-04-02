package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.port.BaseProductOptionPort;
import com.craftbase.restaurantapi.product.port.ProductOptionPort;
import com.craftbase.restaurantapi.product.port.ProductPort;
import com.craftbase.restaurantapi.product.usecase.SaveProductOption;
import com.craftbase.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveProductOptionUseCaseHandler implements UseCaseHandler<UUID, SaveProductOption> {

    private final ProductOptionPort productOptionPort;
    private final BaseProductOptionPort baseProductOptionPort;
    private final ProductPort productPort;
    private final RestaurantFranchisingPort restaurantFranchisingPort;


    @Override
    public UUID handle(SaveProductOption useCase) {

        verify(useCase);
        BaseProductOptionDto baseProductOptionDto = baseProductOptionPort
                .getBaseProductOptionById(useCase.getBaseProductOptionId());
        useCase.setPrice(useCase.getPrice() == null ? baseProductOptionDto.getDefaultPrice() : useCase.getPrice());
        return productOptionPort.saveProductOption(useCase);
    }

    private void verify(SaveProductOption useCase) {
        boolean isRestaurantFranchisingExistById = restaurantFranchisingPort
                .isRestaurantFranchisingExistById(useCase.getRestaurantFranchisingId());
        if (!isRestaurantFranchisingExistById) {
            throw new RestaurantApiException(ErrorCode.RESTAURANT_FRANCHISING_NOT_FOUND_ERROR);
        }
        ProductDto productDto = productPort.getProductById(useCase.getProductId());
        if (!useCase.getRestaurantFranchisingId().equals(productDto.getRestaurantFranchisingId())) {
            throw new RestaurantApiException(ErrorCode.RESTAURANT_FRANCHISING_ID_NOT_EQUAL_ERROR);
        }
    }
}
