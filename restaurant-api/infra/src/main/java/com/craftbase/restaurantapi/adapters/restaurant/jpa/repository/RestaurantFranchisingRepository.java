package com.craftbase.restaurantapi.adapters.restaurant.jpa.repository;

import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantFranchisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantFranchisingRepository extends JpaRepository<RestaurantFranchisingEntity, UUID> {
}