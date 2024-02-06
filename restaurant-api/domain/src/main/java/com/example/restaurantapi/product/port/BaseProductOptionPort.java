package com.example.restaurantapi.product.port;

import com.example.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.example.restaurantapi.product.usecase.SaveBaseProductOption;
import java.util.UUID;

public interface BaseProductOptionPort {
    UUID saveBaseProductOption(SaveBaseProductOption saveBaseProduct);

    BaseProductOptionDto getBaseProductOptionById(UUID baseProductOptionId);

}
