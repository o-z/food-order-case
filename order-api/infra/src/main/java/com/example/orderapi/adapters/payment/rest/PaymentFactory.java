package com.example.orderapi.adapters.payment.rest;

import com.example.orderapi.payment.model.dto.PaymentDto;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.dto.PaymentReportDto;
import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.model.usecase.PaymentCreate;
import com.example.orderapi.payment.model.usecase.PaymentRefund;
import com.example.orderapi.payment.port.PaymentPort;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentFactory implements PaymentPort {

    private static final Map<GatewayType, Payment> paymentPortMap = new EnumMap<>(GatewayType.class);
    private final List<Payment> paymentServices;

    @PostConstruct
    public void initPaymentServices() {
        for (Payment paymentService : paymentServices) {
            paymentPortMap.put(paymentService.getPaymentGatewayType(), paymentService);
        }
    }

    @Override
    public PaymentDto pay(PaymentCreate paymentCreate, GatewayType gatewayType) {
        return paymentPortMap.get(gatewayType).pay(paymentCreate);
    }

    @Override
    public PaymentRefundDto refund(PaymentRefund paymentRefund, GatewayType gatewayType) {
        return paymentPortMap.get(gatewayType).refund(paymentRefund);
    }

    @Override
    public PaymentReportDto getPaymentReport(String paymentId, GatewayType gatewayType) {
        return paymentPortMap.get(gatewayType).getPaymentReport(paymentId);
    }

}
