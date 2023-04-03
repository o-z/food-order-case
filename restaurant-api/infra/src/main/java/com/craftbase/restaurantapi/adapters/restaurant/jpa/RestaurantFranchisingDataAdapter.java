package com.craftbase.restaurantapi.adapters.restaurant.jpa;

import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantFranchisingEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantFranchisingRepository;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.craftbase.restaurantapi.common.exception.ErrorCode;
import com.craftbase.restaurantapi.common.exception.RestaurantApiException;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.craftbase.restaurantapi.restaurant.port.RestaurantFranchisingPort;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantFranchisingDataAdapter implements RestaurantFranchisingPort {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantFranchisingRepository restaurantFranchisingRepository;

    @Transactional
    @Override
    public UUID saveRestaurantFranchising(SaveRestaurantFranchising saveRestaurantFranchising) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(saveRestaurantFranchising.getRestaurantId())
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.RESTAURANT_NOT_FOUND_ERROR));

        verifyDefinedNameFranchising(saveRestaurantFranchising, restaurantEntity);
        RestaurantFranchisingEntity restaurantFranchisingEntity = RestaurantFranchisingEntity.builder()
                .name(saveRestaurantFranchising.getName())
                .desc(saveRestaurantFranchising.getDesc())
                .country(saveRestaurantFranchising.getCountry())
                .address(saveRestaurantFranchising.getAddress())
                .restaurantEntity(restaurantEntity)
                .status(saveRestaurantFranchising.getStatus())
                .build();
        return restaurantFranchisingRepository.save(restaurantFranchisingEntity).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public RestaurantFranchisingDto getRestaurantFranchisingById(UUID restaurantFranchisingId) {
        return restaurantFranchisingRepository.findById(restaurantFranchisingId)
                .orElseThrow(() -> new RestaurantApiException(ErrorCode.RESTAURANT_FRANCHISING_NOT_FOUND_ERROR)).toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isRestaurantFranchisingExistById(UUID restaurantFranchisingId) {
        return restaurantFranchisingRepository.existsById(restaurantFranchisingId);
    }

    private void verifyDefinedNameFranchising(SaveRestaurantFranchising saveRestaurantFranchising,
                                              RestaurantEntity restaurantEntity) {

        boolean hasDefinedNameFranchising = restaurantEntity.getRestaurantFranchisingEntities().stream()
                .anyMatch(franchising -> saveRestaurantFranchising.getName().equals(franchising.getName()));
        if (hasDefinedNameFranchising) {
            throw new RestaurantApiException(ErrorCode.ALREADY_DEFINED_NAME_RESTAURANT_FRANCHISING_ERROR);
        }
    }

}
