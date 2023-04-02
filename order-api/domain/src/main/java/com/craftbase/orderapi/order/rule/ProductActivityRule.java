package com.craftbase.orderapi.order.rule;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.basket.model.dto.BasketProductDto;
import com.craftbase.orderapi.basket.port.BasketPort;
import com.craftbase.orderapi.common.exception.ErrorCode;
import com.craftbase.orderapi.common.exception.OrderApiException;
import com.craftbase.orderapi.common.model.dto.RuleResultDto;
import com.craftbase.orderapi.common.rule.IRule;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.order.usecase.PlaceOrder;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import com.craftbase.orderapi.product.model.dto.ProductOptionDto;
import com.craftbase.orderapi.product.model.enums.ProductStatus;
import com.craftbase.orderapi.product.port.ProductPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@DomainComponent
@RequiredArgsConstructor
public class ProductActivityRule implements IRule<PlaceOrder> {

    private final BasketPort basketPort;
    private final ProductPort productPort;

    @Override
    public RuleResultDto validate(PlaceOrder useCase) {
        BasketDto basket = basketPort.getBasketByUserId(useCase.getUserId());

        checkIsBasketEmpty(basket);
        Set<UUID> productIds = basket.getBasketProducts().stream().map(BasketProductDto::getProductId).collect(Collectors.toSet());
        Map<UUID, ProductDto> productByIdMap = productPort.getProductByIdSet(productIds);

        if (isAnyProductsPassive(basket, productByIdMap)) {
            return RuleResultDto.builder().isSuccess(false).errorCode(ErrorCode.PRODUCT_NOT_ACTIVE_ERROR).build();
        }
        if (isAnyProductOptionsPassive(basket, productByIdMap)) {
            return RuleResultDto.builder().isSuccess(false).errorCode(ErrorCode.PRODUCT_OPTION_NOT_ACTIVE_ERROR).build();
        }
        return RuleResultDto.builder().isSuccess(true).build();
    }

    @Override
    public Integer getRuleSortNumber() {
        return 1;
    }

    @Override
    public boolean isValidFor(Object obj) {
        return obj instanceof PlaceOrder;
    }

    private boolean isAnyProductsPassive(BasketDto basket, Map<UUID, ProductDto> productByIdMap) {
        return basket.getBasketProducts().stream()
                .anyMatch(basketProductDto -> {
                    ProductDto productDto = productByIdMap.get(basketProductDto.getProductId());
                    if (productDto == null) {
                        throw new OrderApiException(ErrorCode.PRODUCT_NOT_FOUND_ERROR);
                    } else {
                        return productDto.getStatus().equals(ProductStatus.PASSIVE);
                    }
                });
    }

    private boolean isAnyProductOptionsPassive(BasketDto basket, Map<UUID, ProductDto> productByIdMap) {
        return basket.getBasketProducts().stream()
                .filter(basketProductDto -> basketProductDto.getBasketProductOptions() != null)
                .anyMatch(basketProductDto -> basketProductDto.getBasketProductOptions().stream()
                        .anyMatch(basketProductOptionDto -> {
                            ProductOptionDto productOptionDto = productByIdMap.get(basketProductDto.getProductId())
                                    .getProductOptionDtoMap().get(basketProductOptionDto.getProductOptionId());
                            if (productOptionDto == null) {
                                throw new OrderApiException(ErrorCode.PRODUCT_OPTION_NOT_FOUND_ERROR);
                            } else {
                                return productOptionDto.getStatus().equals(ProductStatus.PASSIVE);
                            }
                        }));
    }


    private void checkIsBasketEmpty(BasketDto basket) {
        if (basket.getBasketProducts() == null || basket.getBasketProducts().isEmpty()) {
            throw new OrderApiException(ErrorCode.BASKET_IS_EMPTY_ERROR);
        }
    }


}
