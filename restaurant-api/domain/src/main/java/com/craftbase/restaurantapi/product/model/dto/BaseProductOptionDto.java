package com.craftbase.restaurantapi.product.model.dto;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseProductOptionDto implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private BigDecimal defaultPrice;
    private PriceType defaultPriceType;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}