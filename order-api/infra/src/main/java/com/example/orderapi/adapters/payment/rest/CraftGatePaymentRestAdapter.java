package com.example.orderapi.adapters.payment.rest;

import com.example.orderapi.adapters.payment.model.properties.CraftGateProperties;
import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.payment.model.dto.PaymentDto;
import com.example.orderapi.payment.model.dto.PaymentRefundDto;
import com.example.orderapi.payment.model.dto.PaymentReportDto;
import com.example.orderapi.payment.model.enums.GatewayType;
import com.example.orderapi.payment.model.enums.OrderPaymentStatus;
import com.example.orderapi.payment.model.usecase.PaymentCreate;
import com.example.orderapi.payment.model.usecase.PaymentRefund;
import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.model.PaymentStatus;
import io.craftgate.model.RefundDestinationType;
import io.craftgate.model.RefundStatus;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.RefundPaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentRefundResponse;
import io.craftgate.response.PaymentResponse;
import io.craftgate.response.ReportingPaymentResponse;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        PaymentResponse response = craftgate.payment().createPayment(request);
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
        PaymentRefundResponse response = craftgate.payment().refundPayment(request);
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
