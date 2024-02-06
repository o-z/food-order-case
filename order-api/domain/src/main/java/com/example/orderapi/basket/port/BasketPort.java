package com.example.orderapi.basket.port;

import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import java.util.UUID;

public interface BasketPort {
    BasketDto getBasketByUserId(UUID userId);

    void clearBasket(UUID basketId);

    void addProductToBasket(AddProductToBasket addProductToBasket);
}
