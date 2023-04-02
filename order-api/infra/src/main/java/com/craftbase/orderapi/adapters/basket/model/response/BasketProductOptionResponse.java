package com.craftbase.orderapi.adapters.basket.model.response;

import com.craftbase.orderapi.basket.model.dto.BasketProductOptionDto;
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
public class BasketProductOptionResponse implements Serializable {
    private UUID id;
    private UUID productOptionId;
    private BigDecimal calculatedPrice;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BasketProductOptionResponse from(BasketProductOptionDto basketProductOptionDto) {
        return BasketProductOptionResponse.builder()
                .id(basketProductOptionDto.getId())
                .calculatedPrice(basketProductOptionDto.getCalculatedPrice())
                .productOptionId(basketProductOptionDto.getProductOptionId())
                .createDate(basketProductOptionDto.getCreateDate())
                .updateDate(basketProductOptionDto.getUpdateDate())
                .build();
    }
}
