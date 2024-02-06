package com.example.restaurantapi.adapters;

import com.example.restaurantapi.product.model.dto.BaseProductDto;
import com.example.restaurantapi.product.port.BaseProductPort;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseProductFakeAdapter implements BaseProductPort {

    private Map<UUID, BaseProductDto> baseProducts = new HashMap<>();

    @Override
    public UUID saveBaseProduct(SaveBaseProduct saveBaseProduct) {
        BaseProductDto baseProductDto = BaseProductDto.builder()
                .id(UUID.randomUUID())
                .name(saveBaseProduct.getName())
                .desc(saveBaseProduct.getDesc())
                .defaultPrice(saveBaseProduct.getDefaultPrice())
                .defaultPriceType(saveBaseProduct.getDefaultPriceType())
                .imageUrl(saveBaseProduct.getImageUrl())
                .categoryId(saveBaseProduct.getCategoryId())
                .restaurantId(saveBaseProduct.getRestaurantId())
                .status(saveBaseProduct.getStatus())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .products(new ArrayList<>())
                .baseProductOptions(new ArrayList<>())
                .build();
        baseProducts.put(baseProductDto.getId(), baseProductDto);
        return baseProductDto.getId();
    }

    @Override
    public BaseProductDto getBaseProductById(UUID baseProductId) {
        return baseProducts.get(baseProductId);
    }
}
