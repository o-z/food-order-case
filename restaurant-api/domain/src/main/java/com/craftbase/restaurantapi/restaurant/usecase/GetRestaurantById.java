package com.craftbase.restaurantapi.restaurant.usecase;

import com.craftbase.restaurantapi.common.usecase.UseCase;
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
public class GetRestaurantById implements UseCase, Serializable {
    private UUID restaurantId;
}
