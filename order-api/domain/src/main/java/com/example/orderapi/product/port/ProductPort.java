package com.example.orderapi.product.port;

import com.example.orderapi.product.model.dto.ProductDto;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ProductPort {
    ProductDto getProductById(UUID id);

    Map<UUID, ProductDto> getProductByIdSet(Set<UUID> id);

}
