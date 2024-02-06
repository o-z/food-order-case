package com.example.restaurantapi.adapters.product.jpa;

import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.product.port.ProductOptionPort;
import com.example.restaurantapi.product.usecase.SaveProductOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
