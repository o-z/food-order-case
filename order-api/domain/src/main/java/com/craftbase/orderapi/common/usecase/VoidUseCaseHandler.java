package com.craftbase.orderapi.common.usecase;

public interface VoidUseCaseHandler<T extends UseCase> {
    void handle(T useCase);
}
