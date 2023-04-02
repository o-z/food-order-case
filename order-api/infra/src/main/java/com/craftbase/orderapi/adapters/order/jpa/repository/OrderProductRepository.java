package com.craftbase.orderapi.adapters.order.jpa.repository;

import com.craftbase.orderapi.adapters.order.jpa.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}