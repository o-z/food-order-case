package com.craftbase.restaurantapi.product.port;

import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProduct;

import java.util.UUID;

public interface BaseProductPort {
    UUID saveBaseProduct(SaveBaseProduct saveBaseProduct);

    BaseProductDto getBaseProductById(UUID baseProductId);

}
