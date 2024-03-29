package com.example.orderapi.order.usecase;

import com.example.orderapi.basket.model.dto.BasketProductDto;
import com.example.orderapi.common.usecase.UseCase;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderProduct implements UseCase, Serializable {
    private BigDecimal price;
    private UUID productId;
    private String name;
    private List<CreateOrderProductOption> orderProductOptions;


    public static CreateOrderProduct from(BasketProductDto basketProductDto) {
        return CreateOrderProduct.builder()
                .price(basketProductDto.getCalculatedPrice())
                .productId(basketProductDto.getProductId())
                .name(basketProductDto.getName())
                .orderProductOptions(basketProductDto.getBasketProductOptions() != null ? basketProductDto.getBasketProductOptions().stream()
                        .map(CreateOrderProductOption::from)
                        .toList() : null)
                .build();
    }
}
