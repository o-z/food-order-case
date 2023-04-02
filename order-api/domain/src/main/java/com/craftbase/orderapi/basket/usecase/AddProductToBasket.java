package com.craftbase.orderapi.basket.usecase;

import com.craftbase.orderapi.common.usecase.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductToBasket implements UseCase, Serializable {
    private UUID basketId;
    private UUID productId;
    private List<UUID> productOptionIds;
}
