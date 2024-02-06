package com.example.restaurantapi.adapters.restaurant.model.request;

import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRestaurantFranchisingRequest implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String country;
    @NotEmpty
    private String address;
    @NotEmpty
    private RestaurantStatus status;

    public SaveRestaurantFranchising toModel(UUID restaurantId) {
        return SaveRestaurantFranchising.builder()
                .name(name)
                .desc(desc)
                .country(country)
                .address(address)
                .restaurantId(restaurantId)
                .status(status)
                .build();
    }
}



