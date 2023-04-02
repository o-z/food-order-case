package com.craftbase.restaurantapi.restaurant.usecase;

import com.craftbase.restaurantapi.common.usecase.UseCase;
import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
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
public class SaveRestaurantFranchising implements UseCase, Serializable {
    private String name;
    private String desc;
    private String country;
    private String address;
    private RestaurantStatus status;
    private UUID restaurantId;
}
