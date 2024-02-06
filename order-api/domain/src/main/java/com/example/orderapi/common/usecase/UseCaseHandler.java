package com.example.orderapi.common.usecase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}
