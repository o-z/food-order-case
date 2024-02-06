package com.example.restaurantapi.restaurant.usecase;

import com.example.restaurantapi.common.usecase.UseCase;
import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRestaurant implements UseCase, Serializable {
    private String name;
    private String desc;
    private String imageUrl;
    private RestaurantStatus status;
}
