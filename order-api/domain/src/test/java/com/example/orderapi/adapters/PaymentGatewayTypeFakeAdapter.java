package com.example.orderapi.adapters;

import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.port.PaymentGatewayTypePort;

public class PaymentGatewayTypeFakeAdapter implements PaymentGatewayTypePort {


    @Override
    public GatewayType getPaymentGatewayType() {
        return GatewayType.CRAFTGATE;
    }
}