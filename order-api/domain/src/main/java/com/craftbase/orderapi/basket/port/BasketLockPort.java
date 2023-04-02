package com.craftbase.orderapi.basket.port;

import java.util.UUID;

public interface BasketLockPort {
    void lock(UUID basketId);

    void unlock(UUID basketId);
}
