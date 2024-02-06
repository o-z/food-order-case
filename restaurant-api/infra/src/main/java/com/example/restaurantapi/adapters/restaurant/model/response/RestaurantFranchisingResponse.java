package com.example.restaurantapi.adapters.restaurant.model.response;

import com.example.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
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
public class RestaurantFranchisingResponse implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private String country;
    private String address;
    private RestaurantStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static RestaurantFranchisingResponse from(RestaurantFranchisingDto restaurantFranchisingDto) {

        return RestaurantFranchisingResponse.builder()
                .id(restaurantFranchisingDto.getId())
                .name(restaurantFranchisingDto.getName())
                .desc(restaurantFranchisingDto.getDesc())
                .country(restaurantFranchisingDto.getCountry())
                .address(restaurantFranchisingDto.getAddress())
                .status(restaurantFranchisingDto.getStatus())
                .createDate(restaurantFranchisingDto.getCreateDate())
                .updateDate(restaurantFranchisingDto.getUpdateDate())
                .build();
    }
}
