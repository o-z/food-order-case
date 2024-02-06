package com.example.orderapi.basket.model.dto;

import com.example.orderapi.common.model.dto.BaseDto;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.product.model.dto.ProductDto;
import com.example.orderapi.product.model.dto.ProductOptionDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
public class BasketDto extends BaseDto {
    private BigDecimal calculatedPrice;
    private PriceType priceType;
    private String comment;
    private UUID userId;
    private UUID userAddressId;
    private List<BasketProductDto> basketProducts;


    public static BasketDto setProductDataToBasket(BasketDto basket, Map<UUID, ProductDto> productDtoMap) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BasketProductDto basketProductDto : basket.getBasketProducts()) {
            ProductDto productDto = productDtoMap.get(basketProductDto.getProductId());
            basketProductDto.setCalculatedPrice(productDto.getPrice());
            basketProductDto.setName(productDto.getName());
            totalPrice = totalPrice.add(productDto.getPrice());
            if (basketProductDto.getBasketProductOptions() != null) {
                for (BasketProductOptionDto basketProductOptionDto : basketProductDto.getBasketProductOptions()) {
                    ProductOptionDto productOptionDto = productDtoMap.get(basketProductDto.getProductId())
                            .getProductOptionDtoMap().get(basketProductOptionDto.getProductOptionId());
                    basketProductOptionDto.setName(productOptionDto.getName());
                    basketProductOptionDto.setCalculatedPrice(productOptionDto.getPrice());
                    totalPrice = totalPrice.add(productOptionDto.getPrice());
                }
            }
        }
        basket.setCalculatedPrice(totalPrice);
        return basket;
    }
}
