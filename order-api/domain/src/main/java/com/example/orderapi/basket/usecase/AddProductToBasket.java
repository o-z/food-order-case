package com.example.orderapi.basket.usecase;

import com.example.orderapi.common.usecase.UseCase;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductToBasket implements UseCase, Serializable {
    private UUID basketId;
    private UUID productId;
    private List<UUID> productOptionIds;
}
