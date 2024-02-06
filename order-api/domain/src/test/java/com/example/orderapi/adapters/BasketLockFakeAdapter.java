package com.example.orderapi.adapters;

import com.example.orderapi.basket.port.BasketLockPort;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BasketLockFakeAdapter implements BasketLockPort {

    private final Set<UUID> lockedBaskets = new HashSet<>();

    @Override
    public void lock(UUID basketId) {
        lockedBaskets.add(basketId);
    }

    @Override
    public void unlock(UUID basketId) {
        try {
            lockedBaskets.remove(basketId);
        } catch (Exception e) {
            return;
        }

    }
}