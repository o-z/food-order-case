package com.example.restaurantapi.adapters.restaurant.model.request;

import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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



