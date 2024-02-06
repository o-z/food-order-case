package com.example.orderapi.adapters.basket.jpa.repository;

import com.example.orderapi.adapters.basket.jpa.entity.BasketProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProductEntity, UUID> {
}