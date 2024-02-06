package com.example.orderapi.adapters.payment.environment;

import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.port.PaymentGatewayTypePort;
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
