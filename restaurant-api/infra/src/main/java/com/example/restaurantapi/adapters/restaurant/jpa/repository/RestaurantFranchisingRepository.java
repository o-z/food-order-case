package com.example.restaurantapi.adapters.restaurant.jpa.repository;

import com.example.restaurantapi.adapters.restaurant.jpa.entity.RestaurantFranchisingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantFranchisingRepository extends JpaRepository<RestaurantFranchisingEntity, UUID> {
}