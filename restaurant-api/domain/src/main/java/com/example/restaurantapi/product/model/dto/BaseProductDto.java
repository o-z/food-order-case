package com.example.restaurantapi.product.model.dto;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseProductDto implements Serializable {
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
    private List<ProductDto> products;
    private List<BaseProductOptionDto> baseProductOptions;
}
