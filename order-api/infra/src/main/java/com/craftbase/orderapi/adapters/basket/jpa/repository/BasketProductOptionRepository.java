package com.craftbase.orderapi.adapters.basket.jpa.repository;

import com.craftbase.orderapi.adapters.basket.jpa.entity.BasketProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasketProductOptionRepository extends JpaRepository<BasketProductOptionEntity, UUID> {
}