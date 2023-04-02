package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.common.usecase.UseCase;
import com.craftbase.orderapi.payment.model.dto.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

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
