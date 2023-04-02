package com.craftbase.restaurantapi.product.port;

import com.craftbase.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;

import java.util.UUID;

public interface BaseProductOptionPort {
    UUID saveBaseProductOption(SaveBaseProductOption saveBaseProduct);

    BaseProductOptionDto getBaseProductOptionById(UUID baseProductOptionId);

}
