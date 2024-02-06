package com.example.orderapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("1000", "Internal server error.", 500),
    FIELD_VALIDATION_ERROR("1001", "Field validation error.", 400),
    BASKET_NOT_FOUND_ERROR("1002", "Basket not found by id.", 400),
    BASKET_NOT_FOUND_BY_USERID_ERROR("1003", "Basket not found by userId.", 400),
    ORDER_NOT_FOUND_ERROR("1004", "Order not found by id.", 400),
    PRODUCT_NOT_ACTIVE_ERROR("1005", "Product not active.", 400),
    BASKET_PRICE_ERROR("1006", "Basket price not same with product price.", 400),
    PAYMENT_CHANNEL_ERROR("1007", "Payment channel error.", 400),
    BASKET_LOCK_ERROR("1008", "Basket already locked error.", 400),
    BASKET_IS_EMPTY_ERROR("1009", "Basket is empty error.", 400),
    PRODUCT_NOT_FOUND_ERROR("1010", "Product not found.", 400),
    PRODUCT_OPTION_NOT_FOUND_ERROR("1011", "Product option not found.", 400),
    PRODUCT_OPTION_NOT_ACTIVE_ERROR("1012", "Product option not active.", 400),
    ORDER_ROLLBACK_ERROR("1013", "Order rollback error.", 400),
    PLACE_ORDER_PROCESS_ERROR("1014", "Place order process error.", 400);


    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
