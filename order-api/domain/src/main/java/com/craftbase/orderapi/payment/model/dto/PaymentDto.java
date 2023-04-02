package com.craftbase.orderapi.payment.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PaymentDto extends BasePaymentDto {
    private String id;
    private LocalDateTime createdDate;
    private BigDecimal paidPrice;
    private String transactionId;
    private String orderId;

}
