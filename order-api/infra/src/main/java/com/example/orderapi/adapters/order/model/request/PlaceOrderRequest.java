package com.example.orderapi.adapters.order.model.request;

import com.example.orderapi.order.usecase.PlaceOrder;
import com.example.orderapi.payment.model.dto.CreditCard;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
