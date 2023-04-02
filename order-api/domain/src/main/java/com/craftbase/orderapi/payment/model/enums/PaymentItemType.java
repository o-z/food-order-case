package com.craftbase.orderapi.payment.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentItemType {
    PRODUCT("P"),
    PRODUCT_OPTION("PO");

    private final String sort;
}
