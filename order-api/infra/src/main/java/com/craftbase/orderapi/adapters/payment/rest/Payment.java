package com.craftbase.orderapi.adapters.payment.rest;

import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.dto.PaymentRefundDto;
import com.craftbase.orderapi.payment.model.dto.PaymentReportDto;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;

public interface Payment {
    PaymentDto pay(PaymentCreate paymentCreate);

    PaymentRefundDto refund(PaymentRefund paymentRefund);

    GatewayType getPaymentGatewayType();

    PaymentReportDto getPaymentReport(String paymentId);
}
