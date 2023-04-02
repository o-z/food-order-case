package com.craftbase.orderapi.basket.port;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.basket.usecase.AddProductToBasket;

import java.util.UUID;

public interface BasketPort {
    BasketDto getBasketByUserId(UUID userId);

    void clearBasket(UUID basketId);

    void addProductToBasket(AddProductToBasket addProductToBasket);
}
