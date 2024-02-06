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
public class SaveProductOption implements UseCase, Serializable {
    private String name;
    private String desc;
    private BigDecimal price;
    private PriceType priceType;
    private UUID restaurantFranchisingId;
    private UUID productId;
    private UUID baseProductOptionId;
    private ProductStatus status;
}
