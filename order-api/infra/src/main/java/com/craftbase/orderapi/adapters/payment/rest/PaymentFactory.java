package com.craftbase.orderapi.adapters.payment.rest;

import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.dto.PaymentRefundDto;
import com.craftbase.orderapi.payment.model.dto.PaymentReportDto;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;
import com.craftbase.orderapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
