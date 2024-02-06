package com.example.orderapi.payment.model.enums;


public enum OrderPaymentStatus {
    NO_REFUND,
    NOT_REFUNDED,
    PARTIAL_REFUNDED,
    FULLY_REFUNDED,
    FAILURE,
    SUCCESS,
    INIT_THREEDS,
    CALLBACK_THREEDS,
    WAITING;

}
