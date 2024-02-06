package com.example.orderapi.order.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.orderapi.adapters.BasketFakeAdapter;
import com.example.orderapi.adapters.ProductFakeAdapter;
import com.example.orderapi.adapters.ProductOptionNotFoundFakeAdapter;
import com.example.orderapi.adapters.ProductOptionPassiveFakeAdapter;
import com.example.orderapi.adapters.ProductPassiveFakeAdapter;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.common.model.dto.RuleResultDto;
import com.example.orderapi.order.usecase.PlaceOrder;
import com.example.orderapi.payment.model.dto.CreditCard;
import java.util.UUID;
import org.junit.Test;


public class ProductActivityRuleTest {

    @Test
    public void testProductActivity_WhenProductAndOptionsAreActive_ReturnSuccess() {
        PlaceOrder placeOrder = PlaceOrder.builder()
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .card(CreditCard.builder()
                        .cardHolderName("John Doe")
                        .cardNumber("1234567890123456")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();
        
        ProductActivityRule handler = new ProductActivityRule(new BasketFakeAdapter(), new ProductFakeAdapter());
        RuleResultDto result = handler.validate(placeOrder);

        
        assertTrue(result.isSuccess());
        assertNull(result.getErrorCode());
    }


    @Test
    public void testProductActivity_WhenProductIsPassive_ReturnError() {
        PlaceOrder placeOrder = PlaceOrder.builder()
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .card(CreditCard.builder()
                        .cardHolderName("John Doe")
                        .cardNumber("1234567890123456")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();
        
        ProductActivityRule handler = new ProductActivityRule(new BasketFakeAdapter(), new ProductPassiveFakeAdapter());
        RuleResultDto result = handler.validate(placeOrder);

        
        assertFalse(result.isSuccess());
        assertEquals(ErrorCode.PRODUCT_NOT_ACTIVE_ERROR, result.getErrorCode());
    }

    @Test
    public void testProductActivity_WhenProductOptionIsPassive_ReturnError() {
        PlaceOrder placeOrder = PlaceOrder.builder()
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .card(CreditCard.builder()
                        .cardHolderName("John Doe")
                        .cardNumber("1234567890123456")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();
        ProductActivityRule handler = new ProductActivityRule(new BasketFakeAdapter(), new ProductOptionPassiveFakeAdapter());

        
        RuleResultDto result = handler.validate(placeOrder);
        
        assertFalse(result.isSuccess());
        assertEquals(ErrorCode.PRODUCT_OPTION_NOT_ACTIVE_ERROR, result.getErrorCode());
    }

    @Test
    public void testProductActivity_WhenProductOptionNotFound_ThrowException() {
        PlaceOrder placeOrder = PlaceOrder.builder()
                .userAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"))
                .restaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"))
                .userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"))
                .card(CreditCard.builder()
                        .cardHolderName("John Doe")
                        .cardNumber("1234567890123456")
                        .expireYear("24")
                        .expireMonth("12")
                        .cvc("123")
                        .build())
                .build();
        ProductActivityRule handler = new ProductActivityRule(new BasketFakeAdapter(), new ProductOptionNotFoundFakeAdapter());

        
        OrderApiException exception = assertThrows(OrderApiException.class, () -> handler.validate(placeOrder));

        
        assertEquals(ErrorCode.PRODUCT_OPTION_NOT_FOUND_ERROR.getCode(), exception.getCode());
    }
}
