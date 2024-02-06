package com.example.restaurantapi.product;

import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.common.util.DomainComponent;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.port.ProductPort;
import com.example.restaurantapi.product.usecase.GetProductById;
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
