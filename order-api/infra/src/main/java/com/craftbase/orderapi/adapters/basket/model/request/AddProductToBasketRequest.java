package com.craftbase.orderapi.adapters.basket.model.request;

import com.craftbase.orderapi.basket.usecase.AddProductToBasket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToBasketRequest implements Serializable {
    @NotNull
    private UUID productId;
    private List<UUID> productOptionIds;

    public AddProductToBasket toModel(UUID basketId) {
        return AddProductToBasket.builder()
                .basketId(basketId)
                .productId(productId)
                .productOptionIds(productOptionIds)
                .build();
    }

}
