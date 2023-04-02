package com.craftbase.restaurantapi.product.port;

import com.craftbase.restaurantapi.product.usecase.SaveProductOption;

import java.util.UUID;

public interface ProductOptionPort {
    UUID saveProductOption(SaveProductOption saveProductOption);

}
