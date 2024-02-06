package com.example.orderapi.payment.model.usecase;


import com.example.orderapi.common.usecase.UseCase;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRefund implements UseCase, Serializable {
    private String paymentId;
}
