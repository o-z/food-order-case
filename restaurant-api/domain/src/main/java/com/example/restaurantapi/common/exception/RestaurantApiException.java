package com.example.restaurantapi.common.exception;

import lombok.Data;

@Data
public class RestaurantApiException extends RuntimeException {

    private final String code;
    private final String message;
    private final Integer httpStatusCode;

    public RestaurantApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    public RestaurantApiException(String code, String message, Integer httpStatusCode) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }


    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
