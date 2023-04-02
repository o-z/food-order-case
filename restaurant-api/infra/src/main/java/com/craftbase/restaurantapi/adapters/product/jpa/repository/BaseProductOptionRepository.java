package com.craftbase.restaurantapi.adapters.product.jpa.repository;

import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BaseProductOptionRepository extends JpaRepository<BaseProductOptionEntity, UUID> {
}