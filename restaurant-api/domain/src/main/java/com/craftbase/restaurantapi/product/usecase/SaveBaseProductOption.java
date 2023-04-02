package com.craftbase.restaurantapi.product.usecase;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.common.usecase.UseCase;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBaseProductOption implements UseCase, Serializable {
    private String name;
    private String desc;
    private BigDecimal defaultPrice;
    private PriceType defaultPriceType;
    private UUID baseProductId;
    private ProductStatus status;
}