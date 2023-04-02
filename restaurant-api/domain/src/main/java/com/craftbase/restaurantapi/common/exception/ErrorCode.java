package com.craftbase.restaurantapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("1000", "Internal server error.", 500),
    FIELD_VALIDATION_ERROR("1001", "Field validation error.", 400),
    PRODUCT_NOT_FOUND_ERROR("1002", "Product not found error.", 400),
    RESTAURANT_NOT_FOUND_ERROR("1003", "Restaurant not found error.", 400),
    ALREADY_DEFINED_NAME_RESTAURANT_FRANCHISING_ERROR("1004", "Already defined name for franchising of restaurant.", 400),
    BASE_PRODUCT_NOT_FOUND_ERROR("1005", "Base product not found error.", 400),
    BASE_PRODUCT_OPTION_NOT_FOUND_ERROR("1006", "Base product option not found error.", 400),
    RESTAURANT_FRANCHISING_NOT_FOUND_ERROR("1007", "Restaurant franchising not found error.", 400),
    RESTAURANT_ID_NOT_EQUAL_ERROR("1008", "Restaurant franchising's restaurantId and Base Product Option's restaurantId not equals.", 400),
    RESTAURANT_FRANCHISING_ID_NOT_EQUAL_ERROR("1009", "Restaurant franchising's restaurantFranchisingId and Base Product 's restaurantFranchisingId not equals.", 400);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
