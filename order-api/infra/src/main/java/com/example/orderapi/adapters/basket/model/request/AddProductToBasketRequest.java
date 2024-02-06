package com.example.orderapi.adapters.basket.model.request;

import com.example.orderapi.basket.usecase.AddProductToBasket;
import java.io.Serializable;
import java.util.List;
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
