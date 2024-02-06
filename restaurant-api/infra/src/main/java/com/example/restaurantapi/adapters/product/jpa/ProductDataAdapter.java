package com.example.restaurantapi.adapters.product.jpa;

import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.SaveProduct;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDataAdapter implements ProductPort {
    private final ProductRepository productRepository;
    private final BaseProductRepository baseProductRepository;

    @Transactional(readOnly = true)
    @Override
    public ProductDto getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.PRODUCT_NOT_FOUND_ERROR)).toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, ProductDto> getProductByIdSet(Set<UUID> ids) {
        return productRepository.findAllById(ids).stream()
                .map(ProductEntity::toModel)
                .collect(Collectors.toMap(ProductDto::getId, Function.identity()));
    }

    @Transactional
    @Override
    public UUID saveProduct(SaveProduct saveProduct) {
        BaseProductEntity baseProductEntity = baseProductRepository
                .findById(saveProduct.getBaseProductId())
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.BASE_PRODUCT_NOT_FOUND_ERROR));
        ProductEntity productEntity = ProductEntity.builder()
                .name(saveProduct.getName())
                .desc(saveProduct.getDesc())
                .imageUrl(saveProduct.getImageUrl())
                .price(saveProduct.getPrice())
                .priceType(saveProduct.getPriceType())
                .restaurantFranchisingId(saveProduct.getRestaurantFranchisingId())
                .categoryId(saveProduct.getCategoryId())
                .baseProductEntity(baseProductEntity)
                .status(saveProduct.getStatus())
                .build();
        return productRepository.save(productEntity).getId();
    }
}
