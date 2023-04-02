package com.craftbase.orderapi.adapters.order.jpa.repository;

import com.craftbase.orderapi.adapters.order.jpa.entity.OrderEntity;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Page<OrderEntity> findAllByUserIdOrderByCreateDateDesc(UUID userId, Pageable pageable);

    Page<OrderEntity> findAllByStatusInAndRetryCountLessThanOrderByCreateDateDesc(List<OrderStatus> orderStatuses,
                                                                                  int maxRetryCount,
                                                                                  Pageable pageable);
}