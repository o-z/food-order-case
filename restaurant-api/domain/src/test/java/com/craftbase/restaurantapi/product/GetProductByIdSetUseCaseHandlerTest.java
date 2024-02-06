package com.example.restaurantapi.product;

import com.example.restaurantapi.adapters.ProductFakeAdapter;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.usecase.GetProductByIdSet;
import com.example.restaurantapi.product.usecase.SaveProduct;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GetProductByIdSetUseCaseHandlerTest {
    private final ProductFakeAdapter productFakeAdapter = new ProductFakeAdapter();
    private final GetProductByIdSetUseCaseHandler getProductByIdSetUseCaseHandler =
            new GetProductByIdSetUseCaseHandler(productFakeAdapter);

    @Test
    public void getProductByIdSet_handlesMultipleValidIds_returnsMapOfProducts() {
        // Arrange
        ProductDto product = ProductDto.builder().id(UUID.randomUUID()).name("Product").build();

        productFakeAdapter.saveProduct(SaveProduct.builder().name("Product").build());

        Set<UUID> productIds = productFakeAdapter.getProductIds();

        // Act
        Map<UUID, ProductDto> result = getProductByIdSetUseCaseHandler.handle(new GetProductByIdSet(productIds));

        ProductDto productDto = result.get(productIds.stream().findFirst().get());
        // Assert
        assertEquals(product.getName(), productDto.getName());
    }
}
