package com.craftbase.orderapi.payment.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentCreateItem implements Serializable {
    private String name;
    private String id;
    private BigDecimal price;
}
