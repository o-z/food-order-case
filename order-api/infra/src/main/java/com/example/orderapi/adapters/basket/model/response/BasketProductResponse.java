package com.example.orderapi.adapters.basket.model.response;

import com.example.orderapi.basket.model.dto.BasketProductDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductResponse implements Serializable {

    private UUID id;
    private UUID productId;
    private BigDecimal calculatedPrice;
    private List<BasketProductOptionResponse> basketProductOptions;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    public static BasketProductResponse from(BasketProductDto basketProductDto) {
        return BasketProductResponse.builder()
                .id(basketProductDto.getId())
                .calculatedPrice(basketProductDto.getCalculatedPrice())
                .productId(basketProductDto.getProductId())
                .basketProductOptions(basketProductDto.getBasketProductOptions().stream()
                        .map(BasketProductOptionResponse::from)
                        .toList())
                .createDate(basketProductDto.getCreateDate())
                .updateDate(basketProductDto.getUpdateDate())
                .build();
    }
}
