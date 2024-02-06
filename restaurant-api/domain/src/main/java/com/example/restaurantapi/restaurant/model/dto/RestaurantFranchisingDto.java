package com.example.restaurantapi.restaurant.model.dto;

import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantFranchisingDto implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private String country;
    private String address;
    private UUID restaurantId;
    private RestaurantStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
