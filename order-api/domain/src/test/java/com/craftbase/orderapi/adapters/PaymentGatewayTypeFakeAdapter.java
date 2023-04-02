package com.craftbase.orderapi.adapters;

import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.port.PaymentGatewayTypePort;

public class PaymentGatewayTypeFakeAdapter implements PaymentGatewayTypePort {


    @Override
    public GatewayType getPaymentGatewayType() {
        return GatewayType.CRAFTGATE;
    }
}