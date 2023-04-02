package com.craftbase.orderapi.payment.model.dto;

import com.craftbase.orderapi.common.exception.OrderApiException;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasePaymentDto implements Serializable {
    private boolean isSuccess;
    private OrderApiException error;
}
