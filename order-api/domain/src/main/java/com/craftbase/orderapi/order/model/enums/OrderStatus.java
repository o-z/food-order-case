package com.craftbase.orderapi.order.model.enums;

public enum OrderStatus {
    INITIAL,
    ERROR,
    ROLLBACK,
    ROLLBACK_SUCCESS,
    ROLLBACK_FAILED,
    PAYMENT_FAILED,
    RECEIVED,
    WAITING_APPROVE,
    APPROVED,
    PREPARING,
    ON_DELIVERY,
    DELIVERED,
    CANCELED
}
