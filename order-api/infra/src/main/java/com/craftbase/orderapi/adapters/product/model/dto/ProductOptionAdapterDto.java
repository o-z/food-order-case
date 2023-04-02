package com.craftbase.orderapi.adapters.product.model.dto;

import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.product.model.dto.ProductOptionDto;
import com.craftbase.orderapi.product.model.enums.ProductStatus;
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
public class ProductOptionAdapterDto implements Serializable {

    private UUID id;
    private String name;
    private String desc;
    private UUID restaurantFranchisingId;
    private BigDecimal price;
    private PriceType priceType;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ProductOptionDto toModel() {
        return ProductOptionDto.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .price(price)
                .priceType(priceType)
                .restaurantFranchisingId(restaurantFranchisingId)
                .status(status)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
