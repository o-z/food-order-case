package com.craftbase.restaurantapi.adapters.restaurant.model.response;

import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
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
public class RestaurantResponse implements Serializable {
    private UUID id;
    private String name;
    private String desc;
    private String imageUrl;
    private RestaurantStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<RestaurantFranchisingResponse> restaurantFranchisings;


    public static RestaurantResponse from(RestaurantDto restaurantDto) {
        return RestaurantResponse.builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .desc(restaurantDto.getDesc())
                .imageUrl(restaurantDto.getImageUrl())
                .status(restaurantDto.getStatus())
                .createDate(restaurantDto.getCreateDate())
                .updateDate(restaurantDto.getUpdateDate())
                .restaurantFranchisings(restaurantDto.getRestaurantFranchisings().stream()
                        .map(RestaurantFranchisingResponse::from)
                        .toList())
                .build();
    }
}
