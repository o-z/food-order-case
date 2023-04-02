package com.craftbase.orderapi.basket.rule.model;

import com.craftbase.orderapi.basket.usecase.AddProductToBasket;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BasketRuleDto {
    private AddProductToBasket addProductToBasket;
    private ProductDto productDto;
}
