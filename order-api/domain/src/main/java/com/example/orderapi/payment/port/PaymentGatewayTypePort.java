package com.example.orderapi.payment.port;

import com.example.orderapi.payment.model.enums.GatewayType;

public interface PaymentGatewayTypePort {
    GatewayType getPaymentGatewayType();
}
