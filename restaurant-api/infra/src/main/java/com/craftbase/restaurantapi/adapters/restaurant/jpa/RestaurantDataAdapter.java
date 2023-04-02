package com.craftbase.restaurantapi.adapters.restaurant.jpa;

import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.craftbase.restaurantapi.restaurant.port.RestaurantPort;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantDataAdapter implements RestaurantPort {

    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    @Override
    public RestaurantDto getRestaurantById(UUID id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.RESTAURANT_NOT_FOUND_ERROR))
                .toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isRestaurantExistById(UUID id) {
        return restaurantRepository.existsById(id);
    }

    @Transactional
    @Override
    public UUID saveRestaurant(SaveRestaurant saveRestaurant) {
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .name(saveRestaurant.getName())
                .desc(saveRestaurant.getDesc())
                .imageUrl(saveRestaurant.getImageUrl())
                .status(saveRestaurant.getStatus())
                .build();
        return restaurantRepository.save(restaurantEntity).getId();
    }

}
