package com.example.restaurantapi.product.usecase;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.common.usecase.UseCase;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
