package com.example.orderapi.payment.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

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
