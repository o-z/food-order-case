package com.example.restaurantapi.adapters.product.model.response;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.ProductOptionDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionResponse implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private BigDecimal price;
    private PriceType priceType;
    private UUID restaurantFranchisingId;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ProductOptionResponse from(ProductOptionDto productOptionDto) {

        return ProductOptionResponse.builder()
                .id(productOptionDto.getId())
                .name(productOptionDto.getName())
                .desc(productOptionDto.getDesc())
                .price(productOptionDto.getPrice())
                .priceType(productOptionDto.getPriceType())
                .restaurantFranchisingId(productOptionDto.getRestaurantFranchisingId())
                .status(productOptionDto.getStatus())
                .createDate(productOptionDto.getCreateDate())
                .updateDate(productOptionDto.getUpdateDate())
                .build();
    }
}
