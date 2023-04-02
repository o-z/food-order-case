package com.craftbase.orderapi.payment.port;

import com.craftbase.orderapi.payment.model.enums.GatewayType;

public interface PaymentGatewayTypePort {
    GatewayType getPaymentGatewayType();
}
