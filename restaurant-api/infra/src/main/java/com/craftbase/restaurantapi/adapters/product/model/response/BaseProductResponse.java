package com.craftbase.restaurantapi.adapters.product.model.response;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseProductResponse implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private BigDecimal defaultPrice;
    private PriceType defaultPriceType;
    private String imageUrl;
    private UUID categoryId;
    private UUID restaurantId;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<ProductResponse> products;
    private List<BaseProductOptionResponse> baseProductOptions;

    public BaseProductResponse from(BaseProductDto baseProductDto) {
        return BaseProductResponse.builder()
                .id(baseProductDto.getId())
                .name(baseProductDto.getName())
                .desc(baseProductDto.getDesc())
                .defaultPrice(baseProductDto.getDefaultPrice())
                .defaultPriceType(baseProductDto.getDefaultPriceType())
                .imageUrl(baseProductDto.getImageUrl())
                .categoryId(baseProductDto.getCategoryId())
                .restaurantId(baseProductDto.getRestaurantId())
                .status(baseProductDto.getStatus())
                .createDate(baseProductDto.getCreateDate())
                .updateDate(baseProductDto.getUpdateDate())
                .products(baseProductDto.getProducts().stream()
                        .map(ProductResponse::from)
                        .toList())
                .baseProductOptions(baseProductDto.getBaseProductOptions().stream()
                        .map(BaseProductOptionResponse::from)
                        .toList())
                .build();
    }
}
