package com.craftbase.restaurantapi.adapters.product.model.response;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private String imageUrl;
    private UUID categoryId;
    private UUID restaurantFranchisingId;
    private BigDecimal price;
    private PriceType priceType;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Map<UUID, ProductOptionResponse> productOptionMap;


    public static ProductResponse from(ProductDto productDto) {
        return ProductResponse.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .desc(productDto.getDesc())
                .imageUrl(productDto.getImageUrl())
                .categoryId(productDto.getCategoryId())
                .restaurantFranchisingId(productDto.getRestaurantFranchisingId())
                .price(productDto.getPrice())
                .priceType(productDto.getPriceType())
                .status(productDto.getStatus())
                .createDate(productDto.getCreateDate())
                .updateDate(productDto.getUpdateDate())
                .productOptionMap(productDto.getProductOptionDtoMap().values().stream()
                        .map(ProductOptionResponse::from)
                        .collect(Collectors.toMap(ProductOptionResponse::getId, Function.identity())))
                .build();
    }
}
