package com.craftbase.restaurantapi.product.port;

import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.usecase.SaveProduct;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ProductPort {
    ProductDto getProductById(UUID id);

    Map<UUID, ProductDto> getProductByIdSet(Set<UUID> ids);

    UUID saveProduct(SaveProduct saveProduct);

}
