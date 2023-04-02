package com.craftbase.orderapi.payment.model.usecase;


import com.craftbase.orderapi.common.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PaymentRefund implements UseCase, Serializable {
    private String paymentId;
}
