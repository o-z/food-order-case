package com.example.restaurantapi.product;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.GetProductByIdSet;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetProductByIdSetUseCaseHandler implements UseCaseHandler<Map<UUID, ProductDto>, GetProductByIdSet> {
    private final ProductPort productPort;

    @Override
    public Map<UUID, ProductDto> handle(GetProductByIdSet useCase) {
        return productPort.getProductByIdSet(useCase.getIds());
    }
}
