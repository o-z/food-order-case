package com.craftbase.restaurantapi.adapters.product.jpa.repository;

import com.craftbase.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}