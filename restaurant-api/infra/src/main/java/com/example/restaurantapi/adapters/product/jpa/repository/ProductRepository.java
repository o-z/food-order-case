package com.example.restaurantapi.adapters.product.jpa.repository;

import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}