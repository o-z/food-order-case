package com.craftbase.orderapi.adapters.product.rest;

import com.craftbase.orderapi.adapters.product.client.ProductApiClient;
import com.craftbase.orderapi.adapters.product.model.dto.ProductAdapterDto;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import com.craftbase.orderapi.product.port.ProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRestAdapter implements ProductPort {

    private final ProductApiClient productApiClient;

    @Override
    public ProductDto getProductById(UUID id) {
        return productApiClient.getProductById(id).toModel();
    }

    @Override
    public Map<UUID, ProductDto> getProductByIdSet(Set<UUID> ids) {

        return productApiClient.getProductByIdSet(ids).values().stream()
                .map(ProductAdapterDto::toModel)
                .collect(Collectors.toMap(ProductDto::getId, Function.identity()));
    }
}
