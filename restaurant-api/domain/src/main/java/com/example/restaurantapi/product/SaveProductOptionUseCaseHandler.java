package com.example.restaurantapi.product;

import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.port.BaseProductOptionPort;
import com.example.restaurantapi.product.port.ProductOptionPort;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.SaveProductOption;
import com.example.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

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
