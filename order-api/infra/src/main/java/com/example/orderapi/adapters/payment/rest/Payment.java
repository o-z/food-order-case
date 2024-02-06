package com.example.orderapi.adapters.payment.rest;

import com.example.orderapi.payment.model.dto.PaymentDto;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.dto.PaymentReportDto;
import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.model.usecase.PaymentCreate;
import com.example.orderapi.payment.model.usecase.PaymentRefund;

public interface Payment {
    PaymentDto pay(PaymentCreate paymentCreate);

    PaymentRefundDto refund(PaymentRefund paymentRefund);

    GatewayType getPaymentGatewayType();

    PaymentReportDto getPaymentReport(String paymentId);
}
