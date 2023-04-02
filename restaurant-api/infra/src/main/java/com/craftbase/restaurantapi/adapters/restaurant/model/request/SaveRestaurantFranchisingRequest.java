package com.craftbase.restaurantapi.adapters.restaurant.model.request;

import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

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



