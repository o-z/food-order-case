package com.example.orderapi.adapters.order.event.producer;

import com.example.orderapi.order.port.OrderRollbackPort;
import com.example.orderapi.order.usecase.OrderRollback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRollbackEventProducerAdapter implements OrderRollbackPort {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.orderRollback}")
    private String orderRollbackTopic;

    @Override
    public void send(OrderRollback orderRollback) {
        log.info("Send orderRollback topic orderId : {}", orderRollback.getOrderId());
        kafkaTemplate.send(orderRollbackTopic, orderRollback);
    }
}
