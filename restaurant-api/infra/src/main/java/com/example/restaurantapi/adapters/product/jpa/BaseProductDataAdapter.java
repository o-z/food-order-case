package com.example.restaurantapi.adapters.product.jpa;

import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.product.model.dto.BaseProductDto;
import com.example.restaurantapi.product.port.BaseProductPort;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseProductDataAdapter implements BaseProductPort {

    private final BaseProductRepository baseProductRepository;

    @Transactional
    @Override
    public UUID saveBaseProduct(SaveBaseProduct saveBaseProduct) {

        BaseProductEntity baseProductEntity = BaseProductEntity.builder()
                .name(saveBaseProduct.getName())
                .desc(saveBaseProduct.getDesc())
                .imageUrl(saveBaseProduct.getImageUrl())
                .defaultPrice(saveBaseProduct.getDefaultPrice())
                .defaultPriceType(saveBaseProduct.getDefaultPriceType())
                .categoryId(saveBaseProduct.getCategoryId())
                .restaurantId(saveBaseProduct.getRestaurantId())
                .status(saveBaseProduct.getStatus())
                .build();
        return baseProductRepository.save(baseProductEntity).getId();

    }

    @Transactional(readOnly = true)
    @Override
    public BaseProductDto getBaseProductById(UUID baseProductId) {
        return baseProductRepository.findById(baseProductId)
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.BASE_PRODUCT_NOT_FOUND_ERROR)).toModel();

    }

}
