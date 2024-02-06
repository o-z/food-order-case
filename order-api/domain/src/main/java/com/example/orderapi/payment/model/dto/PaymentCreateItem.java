package com.example.orderapi.payment.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCreateItem implements Serializable {
    private String name;
    private String id;
    private BigDecimal price;
}
