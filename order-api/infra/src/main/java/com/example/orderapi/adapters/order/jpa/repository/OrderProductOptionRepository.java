package com.example.orderapi.adapters.order.jpa.repository;

import com.example.orderapi.adapters.order.jpa.entity.OrderProductOptionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductOptionRepository extends JpaRepository<OrderProductOptionEntity, UUID> {
}