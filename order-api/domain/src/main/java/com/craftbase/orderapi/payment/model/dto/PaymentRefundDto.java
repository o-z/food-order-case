package com.craftbase.orderapi.payment.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class PaymentRefundDto extends BasePaymentDto {
    private String id;
    private LocalDateTime createdDate;
    private BigDecimal refundPrice;

}
