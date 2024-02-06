package com.example.orderapi.basket.rule.model;

import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.product.model.dto.ProductDto;
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
