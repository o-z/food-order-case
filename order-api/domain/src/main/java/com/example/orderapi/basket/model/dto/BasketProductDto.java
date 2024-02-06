package com.example.orderapi.basket.model.dto;

import com.example.orderapi.common.model.dto.BaseDto;
import java.math.BigDecimal;
import java.util.List;
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
public class BasketProductDto extends BaseDto {

    private UUID productId;
    private String name;
    private BigDecimal calculatedPrice;
    private List<BasketProductOptionDto> basketProductOptions;
}
