package com.example.orderapi.payment.port;

import com.example.orderapi.payment.model.dto.PaymentDto;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.dto.PaymentReportDto;
import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.model.usecase.PaymentCreate;
import com.example.orderapi.payment.model.usecase.PaymentRefund;

public interface PaymentPort {
    PaymentDto pay(PaymentCreate paymentCreate, GatewayType gatewayType);

    PaymentRefundDto refund(PaymentRefund paymentRefund, GatewayType gatewayType);

    PaymentReportDto getPaymentReport(String paymentId, GatewayType gatewayType);

}
