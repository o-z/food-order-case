package com.example.orderapi.adapters.product.model.dto;

import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.product.model.dto.ProductOptionDto;
import com.example.orderapi.product.model.enums.ProductStatus;
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
