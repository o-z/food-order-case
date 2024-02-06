package com.example.restaurantapi.adapters;

import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.SaveProduct;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ProductFakeAdapter implements ProductPort {
    private final Map<UUID, ProductDto> productMap = new HashMap<>();

    @Override
    public ProductDto getProductById(UUID id) {
        return productMap.get(id);
    }

    public Set<UUID> getProductIds() {
        return productMap.keySet();
    }

    @Override
    public Map<UUID, ProductDto> getProductByIdSet(Set<UUID> ids) {
        Map<UUID, ProductDto> resultMap = new HashMap<>();
        for (UUID id : ids) {
            ProductDto product = productMap.get(id);
            if (product != null) {
                resultMap.put(id, product);
            }
        }
        return resultMap;
    }

    @Override
    public UUID saveProduct(SaveProduct saveProduct) {
        ProductDto product = ProductDto.builder()
                .id(UUID.randomUUID())
                .name(saveProduct.getName())
                .desc(saveProduct.getDesc())
                .imageUrl(saveProduct.getImageUrl())
                .price(saveProduct.getPrice())
                .priceType(saveProduct.getPriceType())
                .restaurantFranchisingId(saveProduct.getRestaurantFranchisingId())
                .categoryId(saveProduct.getCategoryId())
                .status(saveProduct.getStatus())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        productMap.put(product.getId(), product);
        return product.getId();
    }
}
