package com.craftbase.orderapi.payment.model.dto;

import com.craftbase.orderapi.payment.model.enums.OrderPaymentStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PaymentReportDto extends BasePaymentDto {
    private String id;
    private LocalDateTime createdDate;
    private BigDecimal paidPrice;
    private OrderPaymentStatus payStatus;
    private OrderPaymentStatus refundStatus;
    private String transactionId;
    private String orderId;


}
