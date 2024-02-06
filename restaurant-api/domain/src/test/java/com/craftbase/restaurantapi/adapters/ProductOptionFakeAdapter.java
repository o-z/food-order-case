package com.example.restaurantapi.adapters;

import com.example.restaurantapi.product.model.dto.ProductOptionDto;
import com.example.restaurantapi.product.port.ProductOptionPort;
import com.example.restaurantapi.product.usecase.SaveProductOption;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductOptionFakeAdapter implements ProductOptionPort {
    private Map<UUID, ProductOptionDto> productOptions = new HashMap<>();

    @Override
    public UUID saveProductOption(SaveProductOption saveProductOption) {
        UUID id = UUID.randomUUID();
        ProductOptionDto productOption = ProductOptionDto.builder()
                .id(id)
                .name(saveProductOption.getName())
                .desc(saveProductOption.getDesc())
                .price(saveProductOption.getPrice())
                .priceType(saveProductOption.getPriceType())
                .restaurantFranchisingId(saveProductOption.getRestaurantFranchisingId())
                .status(saveProductOption.getStatus())
                .build();
        productOptions.put(id, productOption);
        return id;
    }

    public ProductOptionDto getProductOptionById(UUID id) {
        return productOptions.get(id);
    }
}
