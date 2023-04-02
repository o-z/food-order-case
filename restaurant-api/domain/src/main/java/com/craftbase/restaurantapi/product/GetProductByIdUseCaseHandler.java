package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.common.util.DomainComponent;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.port.ProductPort;
import com.craftbase.restaurantapi.product.usecase.GetProductById;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetProductByIdUseCaseHandler implements UseCaseHandler<ProductDto, GetProductById> {
    private final ProductPort productPort;

    @Override
    public ProductDto handle(GetProductById useCase) {
        return productPort.getProductById(useCase.getId());
    }
}
