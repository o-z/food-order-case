package com.example.restaurantapi.adapters.restaurant.jpa;

import com.example.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.example.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.example.restaurantapi.common.exception.ErrorCode;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.port.RestaurantPort;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
