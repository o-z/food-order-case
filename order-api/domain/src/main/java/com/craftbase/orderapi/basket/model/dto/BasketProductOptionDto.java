package com.craftbase.orderapi.basket.model.dto;

import com.craftbase.orderapi.common.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BasketProductOptionDto extends BaseDto {

    private UUID productOptionId;
    private String name;
    private BigDecimal calculatedPrice;

}
