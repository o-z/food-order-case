package com.example.orderapi.payment.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PaymentRefundDto extends BasePaymentDto {
    private String id;
    private LocalDateTime createdDate;
    private BigDecimal refundPrice;

}
