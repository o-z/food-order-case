package com.example.restaurantapi.adapters.product.jpa.repository;

import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseProductOptionRepository extends JpaRepository<BaseProductOptionEntity, UUID> {
}