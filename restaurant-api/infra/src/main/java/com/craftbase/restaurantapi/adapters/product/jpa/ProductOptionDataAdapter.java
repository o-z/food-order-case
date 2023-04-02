package com.craftbase.restaurantapi.adapters.product.jpa;

import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.ProductOptionEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.ProductOptionRepository;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.product.port.ProductOptionPort;
import com.craftbase.restaurantapi.product.usecase.SaveProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductOptionDataAdapter implements ProductOptionPort {

    private final ProductOptionRepository productOptionRepository;
    private final BaseProductOptionRepository baseProductOptionRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public UUID saveProductOption(SaveProductOption saveProductOption) {
        BaseProductOptionEntity baseProductOptionEntity = baseProductOptionRepository
                .findById(saveProductOption.getBaseProductOptionId())
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.BASE_PRODUCT_OPTION_NOT_FOUND_ERROR));

        ProductEntity productEntity = productRepository.findById(saveProductOption.getProductId())
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.PRODUCT_NOT_FOUND_ERROR));

        ProductOptionEntity productOptionEntity = ProductOptionEntity.builder()
                .name(saveProductOption.getName())
                .desc(saveProductOption.getDesc())
                .price(saveProductOption.getPrice())
                .priceType(saveProductOption.getPriceType())
                .restaurantFranchisingId(saveProductOption.getRestaurantFranchisingId())
                .productEntity(productEntity)
                .baseProductOptionEntity(baseProductOptionEntity)
                .status(saveProductOption.getStatus())
                .build();
        return productOptionRepository.save(productOptionEntity).getId();
    }
}
