package com.example.orderapi.adapters.product.client;

import com.example.orderapi.adapters.product.model.dto.ProductAdapterDto;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "product-api", url = "${microservice.product-api}")
public interface ProductApiClient {

    @GetMapping("/v1/products/{id}")
    ProductAdapterDto getProductById(@PathVariable final UUID id);

    @GetMapping("/v1/products")
    Map<UUID, ProductAdapterDto> getProductByIdSet(@RequestParam Set<UUID> uuids);

}