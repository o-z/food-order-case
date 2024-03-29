package com.example.orderapi.basket.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.orderapi.basket.rule.model.BasketRuleDto;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.common.model.dto.RuleResultDto;
import com.example.orderapi.product.model.dto.ProductDto;
import com.example.orderapi.product.model.dto.ProductOptionDto;
import com.example.orderapi.product.model.enums.ProductStatus;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;

public class BasketProductActivityRuleTest {

    private final BasketProductActivityRule handler = new BasketProductActivityRule();

    @Test
    public void shouldReturnSuccessIfProductAndOptionsAreActive() {
        AddProductToBasket addProductToBasket = new AddProductToBasket(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120010"),
                UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"),
                Arrays.asList(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"),
                        UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003")));
        ProductDto productDto = new ProductDto();
        productDto.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option1 = new ProductOptionDto();
        option1.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"));
        option1.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option2 = new ProductOptionDto();
        option2.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"));
        option2.setStatus(ProductStatus.ACTIVE);
        productDto.setProductOptionDtoMap(Map.of(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"),
                option1, UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"), option2));
        BasketRuleDto basketRuleDto = new BasketRuleDto(addProductToBasket, productDto);

        
        RuleResultDto result = handler.validate(basketRuleDto);

        
        assertTrue(result.isSuccess());
        assertNull(result.getErrorCode());
    }

    @Test
    public void shouldReturnErrorIfProductIsPassive() {
        
        AddProductToBasket addProductToBasket = new AddProductToBasket(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120010"), UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"), Arrays.asList(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"), UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003")));
        ProductDto productDto = new ProductDto();
        productDto.setStatus(ProductStatus.PASSIVE);
        ProductOptionDto option1 = new ProductOptionDto();
        option1.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"));
        option1.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option2 = new ProductOptionDto();
        option2.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"));
        option2.setStatus(ProductStatus.ACTIVE);
        productDto.setProductOptionDtoMap(Map.of(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"), option1, UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"), option2));
        BasketRuleDto basketRuleDto = new BasketRuleDto(addProductToBasket, productDto);

        
        RuleResultDto result = handler.validate(basketRuleDto);

        
        assertFalse(result.isSuccess());
        assertEquals(ErrorCode.PRODUCT_NOT_ACTIVE_ERROR, result.getErrorCode());
    }

    @Test
    public void shouldReturnErrorIfProductOptionIsPassive() {
        
        AddProductToBasket addProductToBasket = new AddProductToBasket(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120010"),
                UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"),
                Arrays.asList(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"),
                        UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003")));
        ProductDto productDto = new ProductDto();
        productDto.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option1 = new ProductOptionDto();
        option1.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"));
        option1.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option2 = new ProductOptionDto();
        option2.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"));
        option2.setStatus(ProductStatus.PASSIVE);
        productDto.setProductOptionDtoMap(Map.of(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"), option1, UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003"), option2));
        BasketRuleDto basketRuleDto = new BasketRuleDto(addProductToBasket, productDto);

        
        RuleResultDto result = handler.validate(basketRuleDto);

        
        assertFalse(result.isSuccess());
        assertEquals(ErrorCode.PRODUCT_OPTION_NOT_ACTIVE_ERROR, result.getErrorCode());
    }

    @Test
    public void shouldThrowExceptionIfProductOptionNotFound() {
        
        AddProductToBasket addProductToBasket = new AddProductToBasket(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120010"),
                UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"),
                Arrays.asList(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"),
                        UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120003")));
        ProductDto productDto = new ProductDto();
        productDto.setStatus(ProductStatus.ACTIVE);
        ProductOptionDto option1 = new ProductOptionDto();
        option1.setId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120002"));
        option1.setStatus(ProductStatus.ACTIVE);
        productDto.setProductOptionDtoMap(Map.of(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"), option1));
        BasketRuleDto basketRuleDto = new BasketRuleDto(addProductToBasket, productDto);


        
        OrderApiException exception = assertThrows(OrderApiException.class, () -> {
            handler.validate(basketRuleDto);
        });

        
        assertEquals(ErrorCode.PRODUCT_OPTION_NOT_FOUND_ERROR.getCode(), exception.getCode());
    }
}
