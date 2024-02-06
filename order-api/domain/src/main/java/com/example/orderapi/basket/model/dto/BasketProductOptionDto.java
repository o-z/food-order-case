package com.example.orderapi.basket.model.dto;

import com.example.orderapi.common.model.dto.BaseDto;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
