package com.example.orderapi.basket.rule;

import com.example.orderapi.basket.rule.model.BasketRuleDto;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.common.model.dto.RuleResultDto;
import com.example.orderapi.common.rule.IRule;
import com.example.orderapi.common.util.DomainComponent;
import com.example.orderapi.product.model.dto.ProductDto;
import com.example.orderapi.product.model.dto.ProductOptionDto;
import com.example.orderapi.product.model.enums.ProductStatus;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class BasketProductActivityRule implements IRule<BasketRuleDto> {

    @Override
    public RuleResultDto validate(BasketRuleDto basketRuleDto) {

        if (basketRuleDto.getProductDto().getStatus().equals(ProductStatus.PASSIVE)) {
            return RuleResultDto.builder().isSuccess(false).errorCode(ErrorCode.PRODUCT_NOT_ACTIVE_ERROR).build();
        }
        if (isAnyProductOptionPassive(basketRuleDto.getAddProductToBasket(), basketRuleDto.getProductDto())) {
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
        return obj instanceof BasketRuleDto;
    }

    private boolean isAnyProductOptionPassive(
        AddProductToBasket addProductToBasket, ProductDto productDto) {
        if (addProductToBasket.getProductOptionIds() == null || addProductToBasket.getProductOptionIds().isEmpty()) {
            return false;
        }
        return addProductToBasket.getProductOptionIds().stream()
                .anyMatch(productOptionId -> {
                    ProductOptionDto productOptionDto = productDto.getProductOptionDtoMap().get(productOptionId);
                    if (productOptionDto == null) {
                        throw new OrderApiException(ErrorCode.PRODUCT_OPTION_NOT_FOUND_ERROR);
                    } else {
                        return productOptionDto.getStatus().equals(ProductStatus.PASSIVE);
                    }
                });
    }

}
