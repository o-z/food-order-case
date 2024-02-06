package com.example.restaurantapi.product.usecase;

import com.example.restaurantapi.common.usecase.UseCase;
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
public class GetProductById implements UseCase, Serializable {
    private UUID id;
}
