package com.example.orderapi.adapters.order.jpa.repository;

import com.example.orderapi.adapters.order.jpa.entity.OrderProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}