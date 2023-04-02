package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.port.ProductPort;
import com.craftbase.restaurantapi.product.usecase.GetProductByIdSet;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@DomainComponent
@RequiredArgsConstructor
public class GetProductByIdSetUseCaseHandler implements UseCaseHandler<Map<UUID, ProductDto>, GetProductByIdSet> {
    private final ProductPort productPort;

    @Override
    public Map<UUID, ProductDto> handle(GetProductByIdSet useCase) {
        return productPort.getProductByIdSet(useCase.getIds());
    }
}
