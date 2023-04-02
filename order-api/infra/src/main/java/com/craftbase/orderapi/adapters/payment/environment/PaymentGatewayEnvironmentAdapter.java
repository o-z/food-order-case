package com.craftbase.orderapi.adapters.payment.environment;

import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.port.PaymentGatewayTypePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGatewayEnvironmentAdapter implements PaymentGatewayTypePort {

    @Value("${payment.gateway.type}")
    private GatewayType gatewayType;

    @Override
    public GatewayType getPaymentGatewayType() {
        return gatewayType;
    }
}
