package com.example.restaurantapi.product.port;

import com.example.restaurantapi.product.usecase.SaveProductOption;
import java.util.UUID;

public interface ProductOptionPort {
    UUID saveProductOption(SaveProductOption saveProductOption);

}
