package com.example.orderapi.adapters.basket.jpa.repository;

import com.example.orderapi.adapters.basket.jpa.entity.BasketEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, UUID> {
    Optional<BasketEntity> findByUserId(UUID userId);
}