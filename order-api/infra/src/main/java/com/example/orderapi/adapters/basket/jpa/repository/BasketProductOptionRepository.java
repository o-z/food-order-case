package com.example.orderapi.adapters.basket.jpa.repository;

import com.example.orderapi.adapters.basket.jpa.entity.BasketProductOptionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductOptionRepository extends JpaRepository<BasketProductOptionEntity, UUID> {
}