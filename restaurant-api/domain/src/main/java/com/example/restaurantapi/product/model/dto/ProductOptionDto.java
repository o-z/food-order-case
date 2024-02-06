package com.example.restaurantapi.product.model.dto;

import com.example.restaurantapi.common.model.enums.PriceType;
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
public class ProductOptionDto implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private BigDecimal price;
    private PriceType priceType;
    private UUID restaurantFranchisingId;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
