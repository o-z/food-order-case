package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.basket.model.dto.BasketProductOptionDto;
import com.craftbase.orderapi.common.usecase.UseCase;
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
public class CreateOrderProductOption implements UseCase, Serializable {
    private BigDecimal price;
    private UUID productOptionId;
    private String name;

    public static CreateOrderProductOption from(BasketProductOptionDto basketProductOptionDto) {
        return CreateOrderProductOption.builder()
                .price(basketProductOptionDto.getCalculatedPrice())
                .productOptionId(basketProductOptionDto.getProductOptionId())
                .name(basketProductOptionDto.getName())
                .build();
    }
}
