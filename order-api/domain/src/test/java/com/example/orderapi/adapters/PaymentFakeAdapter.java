package com.example.orderapi.adapters;

import com.example.orderapi.payment.model.dto.PaymentDto;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.dto.PaymentReportDto;
import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.model.enums.OrderPaymentStatus;
import com.example.orderapi.payment.model.usecase.PaymentCreate;
import com.example.orderapi.payment.model.usecase.PaymentRefund;
import com.example.orderapi.payment.port.PaymentPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentFakeAdapter implements PaymentPort {

    @Override
    public PaymentDto pay(PaymentCreate paymentCreate, GatewayType gatewayType) {
        return PaymentDto.builder()
                .id(UUID.randomUUID().toString())
                .createdDate(LocalDateTime.now())
                .paidPrice(paymentCreate.getPrice())
                .transactionId(paymentCreate.getTransactionId().toString())
                .orderId(UUID.randomUUID().toString())
                .isSuccess(true)
                .build();
    }

    @Override
    public PaymentRefundDto refund(PaymentRefund paymentRefund, GatewayType gatewayType) {
        return PaymentRefundDto.builder()
                .id(UUID.randomUUID().toString())
                .createdDate(LocalDateTime.now())
                .refundPrice(BigDecimal.ONE)
                .isSuccess(true)
                .build();
    }

    @Override
    public PaymentReportDto getPaymentReport(String paymentId, GatewayType gatewayType) {
        return PaymentReportDto.builder()
                .id(paymentId)
                .createdDate(LocalDateTime.now())
                .paidPrice(BigDecimal.ONE)
                .payStatus(OrderPaymentStatus.SUCCESS)
                .refundStatus(OrderPaymentStatus.NOT_REFUNDED)
                .transactionId(UUID.randomUUID().toString())
                .orderId(UUID.randomUUID().toString())
                .build();
    }
}