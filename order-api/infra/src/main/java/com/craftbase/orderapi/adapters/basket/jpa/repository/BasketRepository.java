package com.craftbase.orderapi.adapters.basket.jpa.repository;

import com.craftbase.orderapi.adapters.basket.jpa.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {
    Optional<BasketEntity> findByUserId(UUID userId);
}