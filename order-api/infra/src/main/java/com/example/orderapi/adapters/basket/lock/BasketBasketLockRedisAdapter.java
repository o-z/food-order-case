package com.example.orderapi.adapters.basket.lock;

import com.example.orderapi.basket.port.BasketLockPort;
import com.example.orderapi.common.exception.ErrorCode;
import com.example.orderapi.common.exception.OrderApiException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasketBasketLockRedisAdapter implements BasketLockPort {

    @Qualifier("basketLockRegistry")
    private final RedisLockRegistry redisLockRegistry;

    @Override
    public void lock(UUID basketId) {
        Lock lock = redisLockRegistry.obtain(basketId.toString());

        if (!lock.tryLock()) {
            throw new OrderApiException(ErrorCode.BASKET_LOCK_ERROR);
        }
        log.info("basketId:{} locked", basketId);
    }

    @Override
    public void unlock(UUID basketId) {
        try {
            redisLockRegistry.obtain(basketId.toString()).unlock();
            log.info("basketId:{} unlocked", basketId);
        } catch (RuntimeException e) {
            log.error("basketId:{} unlocked error", basketId, e);
        }


    }
}
