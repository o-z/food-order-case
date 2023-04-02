package com.craftbase.restaurantapi.adapters.restaurant.model.request;

import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRestaurantRequest implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String imageUrl;
    @NotEmpty
    private RestaurantStatus status;

    public SaveRestaurant toModel() {
        return SaveRestaurant.builder()
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .status(status)
                .build();
    }
}



