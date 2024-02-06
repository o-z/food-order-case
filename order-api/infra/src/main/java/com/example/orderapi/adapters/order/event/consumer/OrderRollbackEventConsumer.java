package com.example.orderapi.adapters.order.event.consumer;

import com.example.orderapi.common.usecase.VoidUseCaseHandler;
import com.example.orderapi.order.usecase.OrderRollback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRollbackEventConsumer {
    private final VoidUseCaseHandler<OrderRollback> orderRollbackVoidUseCaseHandler;

    @KafkaListener(
            topics = "${spring.kafka.topic.orderRollback}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaOrderRollbackContainerFactory")
    public void listen(@Payload final OrderRollback orderRollback) {
        log.info("Consume orderRollback topic orderId : {}", orderRollback.getOrderId());
        orderRollbackVoidUseCaseHandler.handle(orderRollback);
    }
}
