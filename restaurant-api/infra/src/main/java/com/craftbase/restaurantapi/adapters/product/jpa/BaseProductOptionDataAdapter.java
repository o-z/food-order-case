package com.craftbase.restaurantapi.adapters.product.jpa;

import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.craftbase.restaurantapi.product.port.BaseProductOptionPort;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BaseProductOptionDataAdapter implements BaseProductOptionPort {

    private final BaseProductOptionRepository baseProductOptionRepository;
    private final BaseProductRepository baseProductRepository;

    @Transactional
    @Override
    public UUID saveBaseProductOption(SaveBaseProductOption saveBaseProduct) {
        BaseProductEntity baseProductEntity = baseProductRepository
                .findById(saveBaseProduct.getBaseProductId())
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.BASE_PRODUCT_NOT_FOUND_ERROR));

        BaseProductOptionEntity baseProductOptionEntity = BaseProductOptionEntity.builder()
                .name(saveBaseProduct.getName())
                .desc(saveBaseProduct.getDesc())
                .defaultPrice(saveBaseProduct.getDefaultPrice())
                .defaultPriceType(saveBaseProduct.getDefaultPriceType())
                .baseProductEntity(baseProductEntity)
                .status(saveBaseProduct.getStatus())
                .build();
        return baseProductOptionRepository.save(baseProductOptionEntity).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public BaseProductOptionDto getBaseProductOptionById(UUID baseProductOptionId) {
        BaseProductOptionEntity baseProductOptionEntity = baseProductOptionRepository
                .findById(baseProductOptionId)
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.BASE_PRODUCT_OPTION_NOT_FOUND_ERROR));
        return baseProductOptionEntity.toModel();
    }
}