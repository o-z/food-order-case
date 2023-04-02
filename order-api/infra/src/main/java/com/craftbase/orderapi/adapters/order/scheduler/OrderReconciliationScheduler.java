package com.craftbase.orderapi.adapters.order.scheduler;

import com.craftbase.orderapi.common.usecase.VoidUseCaseHandler;
import com.craftbase.orderapi.order.usecase.OrderReconciliation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderReconciliationScheduler {

    private final VoidUseCaseHandler<OrderReconciliation> orderReconciliationVoidUseCaseHandler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void computeOrderReconciliation() {
        try {
            log.info("OrderReconciliation started by time:{}", LocalDate.now());
            orderReconciliationVoidUseCaseHandler.handle(OrderReconciliation.builder().build());
            log.info("OrderReconciliation finished by time:{}", LocalDate.now());
        } catch (Exception e) {
            log.error("OrderReconciliation error is occurred by ", e);
        }
    }
}
