package com.example.restaurantapi.common.usecase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
