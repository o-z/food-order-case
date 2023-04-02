package com.craftbase.orderapi.adapters.payment.rest;

import com.craftbase.orderapi.adapters.payment.model.properties.CraftGateProperties;
import com.craftbase.orderapi.common.exception.OrderApiException;
import com.craftbase.orderapi.payment.model.dto.PaymentDto;
import com.craftbase.orderapi.payment.model.dto.PaymentRefundDto;
import com.craftbase.orderapi.payment.model.dto.PaymentReportDto;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import com.craftbase.orderapi.payment.model.enums.OrderPaymentStatus;
import com.craftbase.orderapi.payment.model.usecase.PaymentCreate;
import com.craftbase.orderapi.payment.model.usecase.PaymentRefund;
import io.craftgate.Craftgate;
import io.craftgate.model.*;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.RefundPaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentRefundResponse;
import io.craftgate.response.PaymentResponse;
import io.craftgate.response.ReportingPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CraftGatePaymentRestAdapter implements Payment {

    private final CraftGateProperties craftGateProperties;

    @Override
    public PaymentDto pay(PaymentCreate paymentCreate) {
        log.info("payment call started transactionId:{}", paymentCreate.getTransactionId());

        Craftgate craftgate = new Craftgate(craftGateProperties.getApiKey(), craftGateProperties.getSecretKey(), craftGateProperties.getBaseUrl());
        CreatePaymentRequest request = getCreatePaymentRequest(paymentCreate);
        io.craftgate.response.PaymentResponse response = craftgate.payment().createPayment(request);
        if (response.getPaymentStatus().equals(PaymentStatus.SUCCESS) && ObjectUtils.isEmpty(response.getPaymentError())) {
            log.info("payment call successfully ended transactionId:{}", paymentCreate.getTransactionId());
            return buildSuccessPaymentDto(response);
        } else {
            log.info("payment call failed ended transactionId:{}", paymentCreate.getTransactionId());
            return buildFailedPaymentDto(response);
        }
    }

    @Override
    public PaymentRefundDto refund(PaymentRefund paymentRefund) {
        Craftgate craftgate = new Craftgate(craftGateProperties.getApiKey(), craftGateProperties.getSecretKey(), craftGateProperties.getBaseUrl());
        RefundPaymentRequest request = getRefundPaymentRequest(paymentRefund);
        log.info("payment refund call started paymentId:{}", paymentRefund.getPaymentId());
        io.craftgate.response.PaymentRefundResponse response = craftgate.payment().refundPayment(request);
        log.info("payment refund call ended paymentId:{}", paymentRefund.getPaymentId());

        return buildRefundDto(response);
    }

    @Override
    public GatewayType getPaymentGatewayType() {
        return GatewayType.CRAFTGATE;
    }

    @Override
    public PaymentReportDto getPaymentReport(String paymentId) {
        Craftgate craftgate = new Craftgate(craftGateProperties.getApiKey(), craftGateProperties.getSecretKey(), craftGateProperties.getBaseUrl());
        log.info("payment report call started paymentId:{}", paymentId);
        ReportingPaymentResponse reportingPaymentResponse = craftgate.paymentReporting().retrievePayment(Long.valueOf(paymentId));
        log.info("payment report call ended paymentId:{}", paymentId);

        return buildPaymentReportDto(reportingPaymentResponse);
    }


    private CreatePaymentRequest getCreatePaymentRequest(PaymentCreate paymentCreate) {
        return CreatePaymentRequest.builder()
                .price(paymentCreate.getPrice())
                .paidPrice(paymentCreate.getPrice())
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.valueOf(paymentCreate.getPriceType().name()))
                .conversationId(paymentCreate.getTransactionId().toString())
                .paymentGroup(PaymentGroup.PRODUCT)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(paymentCreate.getCreditCard().getCardHolderName())
                        .cardNumber(paymentCreate.getCreditCard().getCardNumber())
                        .expireYear(paymentCreate.getCreditCard().getExpireYear())
                        .expireMonth(paymentCreate.getCreditCard().getExpireMonth())
                        .cvc(paymentCreate.getCreditCard().getCvc())
                        .build())
                .items(paymentCreate.getPaymentCreateItems().stream()
                        .map(paymentCreateItem -> PaymentItem.builder()
                                .externalId(paymentCreateItem.getId())
                                .name(paymentCreateItem.getName())
                                .price(paymentCreateItem.getPrice())
                                .build()).toList())
                .build();
    }

    private PaymentDto buildFailedPaymentDto(PaymentResponse response) {
        return PaymentDto.builder()
                .isSuccess(Boolean.FALSE)
                .error(new OrderApiException(response.getPaymentError().getErrorCode(), response.getPaymentError().getErrorDescription(), HttpStatus.BAD_REQUEST.value()))
                .build();
    }

    private PaymentDto buildSuccessPaymentDto(PaymentResponse response) {
        return PaymentDto.builder()
                .id(response.getId().toString())
                .createdDate(response.getCreatedDate())
                .paidPrice(response.getPaidPrice())
                .transactionId(response.getTransId())
                .orderId(response.getOrderId())
                .isSuccess(Boolean.TRUE)
                .build();
    }

    private PaymentRefundDto buildRefundDto(PaymentRefundResponse response) {
        return PaymentRefundDto.builder()
                .id(response.getId().toString())
                .createdDate(response.getCreatedDate())
                .refundPrice(response.getRefundPrice())
                .isSuccess(response.getStatus().equals(RefundStatus.SUCCESS))
                .build();
    }

    private PaymentReportDto buildPaymentReportDto(ReportingPaymentResponse reportingPaymentResponse) {
        return PaymentReportDto.builder()
                .id(String.valueOf(reportingPaymentResponse.getId()))
                .createdDate(reportingPaymentResponse.getCreatedDate())
                .paidPrice(reportingPaymentResponse.getPaidPrice())
                .payStatus(OrderPaymentStatus.valueOf(reportingPaymentResponse.getPaymentStatus().name()))
                .refundStatus(OrderPaymentStatus.valueOf(reportingPaymentResponse.getRefundStatus().name()))
                .transactionId(reportingPaymentResponse.getTransId())
                .orderId(reportingPaymentResponse.getOrderId())
                .build();
    }


    private RefundPaymentRequest getRefundPaymentRequest(PaymentRefund paymentRefund) {
        return RefundPaymentRequest.builder()
                .paymentId(Long.valueOf(paymentRefund.getPaymentId()))
                .refundDestinationType(RefundDestinationType.PROVIDER)
                .build();
    }

}
