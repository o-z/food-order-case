package com.craftbase.orderapi.adapters;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.basket.model.dto.BasketProductDto;
import com.craftbase.orderapi.basket.model.dto.BasketProductOptionDto;
import com.craftbase.orderapi.basket.port.BasketPort;
import com.craftbase.orderapi.basket.usecase.AddProductToBasket;
import com.craftbase.orderapi.common.model.enums.PriceType;

import java.math.BigDecimal;
import java.util.*;

public class BasketFakeAdapter implements BasketPort {
    private final Map<UUID, BasketDto> baskets = new HashMap<>();

    @Override
    public BasketDto getBasketByUserId(UUID userId) {
        return BasketDto.builder()
                .id(UUID.fromString("b4a1e188-99b2-4768-b375-a7a7259c94ab"))
                .calculatedPrice(BigDecimal.valueOf(15.0))
                .priceType(PriceType.TRY)
                .comment("This is a comment")
                .userId(userId)
                .userAddressId(UUID.fromString("fedc48db-8411-492f-aa53-1f33a51e281c"))
                .basketProducts(List.of(
                        BasketProductDto.builder()
                                .productId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                                .name("Cheeseburger")
                                .calculatedPrice(BigDecimal.valueOf(10.99))
                                .basketProductOptions(List.of(
                                        BasketProductOptionDto.builder()
                                                .productOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"))
                                                .name("Extra Cheese")
                                                .calculatedPrice(BigDecimal.valueOf(5.0))
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }

    @Override
    public void clearBasket(UUID basketId) {
        BasketDto basket = baskets.get(basketId);
        if (basket != null) {
            basket.setBasketProducts(new ArrayList<>());
            basket.setComment(null);
        }
    }

    @Override
    public void addProductToBasket(AddProductToBasket addProductToBasket) {
        BasketDto basket = baskets.get(addProductToBasket.getBasketId());
        if (basket == null) {
            basket = BasketDto.builder()
                    .calculatedPrice(BigDecimal.ZERO)
                    .priceType(PriceType.TRY)
                    .comment("")
                    .userId(UUID.randomUUID())
                    .userAddressId(UUID.randomUUID())
                    .basketProducts(new ArrayList<>())
                    .build();
            baskets.put(addProductToBasket.getBasketId(), basket);
        }
        List<BasketProductOptionDto> basketProductOptions = new ArrayList<>();
        if (addProductToBasket.getProductOptionIds() != null) {
            for (UUID productOptionId : addProductToBasket.getProductOptionIds()) {
                basketProductOptions.add(BasketProductOptionDto.builder()
                        .productOptionId(productOptionId)
                        .name("Product option " + productOptionId)
                        .calculatedPrice(BigDecimal.TEN)
                        .build());
            }
        }
        BasketProductDto basketProductDto = BasketProductDto.builder()
                .productId(addProductToBasket.getProductId())
                .name("Product " + addProductToBasket.getProductId())
                .calculatedPrice(BigDecimal.TEN)
                .basketProductOptions(basketProductOptions)
                .build();
        basket.getBasketProducts().add(basketProductDto);
        baskets.put(addProductToBasket.getBasketId(), basket);
    }
}