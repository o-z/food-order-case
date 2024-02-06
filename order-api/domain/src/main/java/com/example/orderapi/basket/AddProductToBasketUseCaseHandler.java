package com.example.orderapi.basket;

import com.example.orderapi.basket.port.BasketLockPort;
import com.example.orderapi.basket.port.BasketPort;
import com.example.orderapi.basket.rule.model.BasketRuleDto;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.rule.RuleRunner;
import com.example.orderapi.common.usecase.VoidUseCaseHandler;
import com.example.orderapi.common.util.DomainComponent;
import com.example.orderapi.product.model.dto.ProductDto;
import com.example.orderapi.product.port.ProductPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class AddProductToBasketUseCaseHandler implements VoidUseCaseHandler<AddProductToBasket> {

    private final ProductPort productPort;
    private final BasketPort basketPort;
    private final BasketLockPort basketLockPort;
    private final RuleRunner<BasketRuleDto> ruleRunner;

    @Override
    public void handle(AddProductToBasket addProductToBasket) {

        ProductDto productDto = productPort.getProductById(addProductToBasket.getProductId());
        ruleRunner.validate(new BasketRuleDto(addProductToBasket, productDto));
        basketLockPort.lock(addProductToBasket.getBasketId());
        try {
            basketPort.addProductToBasket(addProductToBasket);
            log.info("added productId: {} to basketId:{} ", addProductToBasket.getProductId(), addProductToBasket.getBasketId());

        } finally {
            basketLockPort.unlock(addProductToBasket.getBasketId());
        }
    }
}
