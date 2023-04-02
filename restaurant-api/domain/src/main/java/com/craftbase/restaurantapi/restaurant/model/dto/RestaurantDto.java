package com.craftbase.restaurantapi.restaurant.model.dto;

import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private String imageUrl;
    private List<RestaurantFranchisingDto> restaurantFranchisings;
    private RestaurantStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
