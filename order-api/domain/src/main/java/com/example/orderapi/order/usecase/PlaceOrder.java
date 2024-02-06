package com.example.orderapi.order.usecase;

import com.example.orderapi.common.usecase.UseCase;
import com.example.orderapi.payment.model.dto.CreditCard;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrder implements UseCase, Serializable {
    private UUID userId;
    private UUID userAddressId;
    private UUID restaurantFranchisingId;
    private CreditCard card;
}
