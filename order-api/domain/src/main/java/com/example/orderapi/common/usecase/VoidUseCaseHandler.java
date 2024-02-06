package com.example.orderapi.common.usecase;

public interface VoidUseCaseHandler<T extends UseCase> {
    void handle(T useCase);
}
