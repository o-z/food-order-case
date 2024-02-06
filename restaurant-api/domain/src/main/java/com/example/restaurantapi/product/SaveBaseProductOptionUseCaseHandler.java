package com.example.restaurantapi.product;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.port.BaseProductOptionPort;
import com.example.restaurantapi.product.usecase.SaveBaseProductOption;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class SaveBaseProductOptionUseCaseHandler implements UseCaseHandler<UUID, SaveBaseProductOption> {
    private final BaseProductOptionPort baseProductOptionPort;

    @Override
    public UUID handle(SaveBaseProductOption useCase) {
        return baseProductOptionPort.saveBaseProductOption(useCase);
    }
}
