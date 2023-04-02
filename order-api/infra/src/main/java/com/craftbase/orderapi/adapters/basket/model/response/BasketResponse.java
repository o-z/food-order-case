package com.craftbase.orderapi.adapters.basket.model.response;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.common.model.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse implements Serializable {

    private UUID id;
    private BigDecimal calculatedPrice;
    private PriceType priceType;
    private String comment;
    private UUID userId;
    private UUID userAddressId;
    private List<BasketProductResponse> basketProducts;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BasketResponse from(BasketDto basketDto) {
        return BasketResponse.builder()
                .id(basketDto.getId())
                .calculatedPrice(basketDto.getCalculatedPrice())
                .priceType(basketDto.getPriceType())
                .comment(basketDto.getComment())
                .userId(basketDto.getUserId())
                .userAddressId(basketDto.getUserAddressId())
                .basketProducts(basketDto.getBasketProducts().stream()
                        .map(BasketProductResponse::from)
                        .toList())
                .createDate(basketDto.getCreateDate())
                .updateDate(basketDto.getUpdateDate())
                .build();
    }
}