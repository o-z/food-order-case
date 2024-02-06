package com.example.restaurantapi.product.port;

import com.example.restaurantapi.product.model.dto.BaseProductDto;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;
import java.util.UUID;

public interface BaseProductPort {
    UUID saveBaseProduct(SaveBaseProduct saveBaseProduct);

    BaseProductDto getBaseProductById(UUID baseProductId);

}
