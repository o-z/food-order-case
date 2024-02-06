package com.example.orderapi.payment.model.dto;

import com.example.orderapi.common.exception.OrderApiException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
