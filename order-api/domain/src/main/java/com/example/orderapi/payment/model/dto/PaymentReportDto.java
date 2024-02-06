package com.example.orderapi.payment.model.dto;

import com.example.orderapi.payment.model.enums.OrderPaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

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
