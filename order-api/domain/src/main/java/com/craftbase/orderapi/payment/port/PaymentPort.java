package com.craftbase.orderapi.payment.port;

import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.dto.PaymentRefundDto;
import com.craftbase.orderapi.payment.model.dto.PaymentReportDto;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;

public interface PaymentPort {
    PaymentDto pay(PaymentCreate paymentCreate, GatewayType gatewayType);

    PaymentRefundDto refund(PaymentRefund paymentRefund, GatewayType gatewayType);

    PaymentReportDto getPaymentReport(String paymentId, GatewayType gatewayType);

}
