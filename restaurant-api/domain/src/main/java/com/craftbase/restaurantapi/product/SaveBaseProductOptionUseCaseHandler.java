package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.port.BaseProductOptionPort;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class SaveBaseProductOptionUseCaseHandler implements UseCaseHandler<UUID, SaveBaseProductOption> {
    private final BaseProductOptionPort baseProductOptionPort;

    @Override
    public UUID handle(SaveBaseProductOption useCase) {
        return baseProductOptionPort.saveBaseProductOption(useCase);
    }
}
