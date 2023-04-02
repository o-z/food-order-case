package com.craftbase.orderapi.adapters.order.model.request;

import com.craftbase.orderapi.order.usecase.PlaceOrder;
import com.craftbase.orderapi.payment.model.dto.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest implements Serializable {

    @NotNull
    private UUID userId;
    @NotNull
    private UUID userAddressId;
    @NotNull
    private UUID restaurantFranchisingId;
    @NotNull
    private CreditCard card;

    public PlaceOrder toModel() {
        return PlaceOrder.builder()
                .userId(userId)
                .userAddressId(userAddressId)
                .restaurantFranchisingId(restaurantFranchisingId)
                .card(card)
                .build();
    }

}
