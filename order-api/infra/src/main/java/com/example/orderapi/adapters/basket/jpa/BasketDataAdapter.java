package com.example.orderapi.adapters.basket.jpa;

import com.example.orderapi.adapters.basket.jpa.entity.BasketEntity;
import com.example.orderapi.adapters.basket.jpa.entity.BasketProductEntity;
import com.example.orderapi.adapters.basket.jpa.entity.BasketProductOptionEntity;
import com.example.orderapi.adapters.basket.jpa.repository.BasketProductOptionRepository;
import com.example.orderapi.adapters.basket.jpa.repository.BasketProductRepository;
import com.example.orderapi.adapters.basket.jpa.repository.BasketRepository;
import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.basket.port.BasketPort;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class BasketDataAdapter implements BasketPort {

    private final BasketRepository basketRepository;
    private final BasketProductRepository basketProductRepository;
    private final BasketProductOptionRepository basketProductOptionRepository;

    @Transactional(readOnly = true)
    @Override
    public BasketDto getBasketByUserId(UUID userId) {
        return basketRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.BASKET_NOT_FOUND_ERROR))
                .toModel();

    }

    @Transactional
    @Override
    public void clearBasket(UUID basketId) {
        BasketEntity basketEntity = basketRepository.findById(basketId)
                .orElseThrow(() -> new OrderApiException(ErrorCode.BASKET_NOT_FOUND_ERROR));
        basketEntity.getBasketProductEntities().clear();
        basketEntity.setComment(null);
        basketRepository.save(basketEntity);
    }

    @Transactional
    @Override
    public void addProductToBasket(AddProductToBasket addProductToBasket) {
        BasketEntity basketEntity = basketRepository.findById(addProductToBasket.getBasketId())
                .orElseThrow(() -> new OrderApiException(ErrorCode.BASKET_NOT_FOUND_ERROR));

        BasketProductEntity savedBasketProductEntity = basketProductRepository
                .save(BasketProductEntity.builder()
                        .productId(addProductToBasket.getProductId())
                        .basketEntity(basketEntity)
                        .build());
        if (!CollectionUtils.isEmpty(addProductToBasket.getProductOptionIds())) {
            basketProductOptionRepository
                    .saveAll(addProductToBasket.getProductOptionIds().stream()
                            .map(productOptionId -> BasketProductOptionEntity.builder()
                                    .productOptionId(productOptionId)
                                    .basketProductEntity(savedBasketProductEntity)
                                    .build())
                            .toList());
        }

    }
}
