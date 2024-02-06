package com.example.restaurantapi.adapters.product.jpa.repository;

import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseProductRepository extends JpaRepository<BaseProductEntity, UUID> {
}