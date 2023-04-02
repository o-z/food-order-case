package com.craftbase.orderapi.basket;

import com.craftbase.orderapi.basket.port.BasketLockPort;
import com.craftbase.orderapi.basket.port.BasketPort;
import com.craftbase.orderapi.basket.rule.model.BasketRuleDto;
import com.craftbase.orderapi.basket.usecase.AddProductToBasket;
import com.craftbase.orderapi.common.rule.RuleRunner;
import com.craftbase.orderapi.common.usecase.VoidUseCaseHandler;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import com.craftbase.orderapi.product.port.ProductPort;
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
