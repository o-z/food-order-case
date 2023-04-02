package com.craftbase.restaurantapi.adapters.product.rest.v1;

import com.craftbase.restaurantapi.adapters.product.model.request.ProductIdSetRequest;
import com.craftbase.restaurantapi.adapters.product.model.request.SaveProductOptionRequest;
import com.craftbase.restaurantapi.adapters.product.model.request.SaveProductRequest;
import com.craftbase.restaurantapi.adapters.product.model.response.ProductResponse;
import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.usecase.GetProductById;
import com.craftbase.restaurantapi.product.usecase.GetProductByIdSet;
import com.craftbase.restaurantapi.product.usecase.SaveProduct;
import com.craftbase.restaurantapi.product.usecase.SaveProductOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final UseCaseHandler<Map<UUID, ProductDto>, GetProductByIdSet> getProductByIdSetUseCaseHandler;
    private final UseCaseHandler<ProductDto, GetProductById> getProductByIdUseCaseHandler;
    private final UseCaseHandler<UUID, SaveProduct> saveProductUseCaseHandler;
    private final UseCaseHandler<UUID, SaveProductOption> saveProductOptionUseCaseHandler;


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable final UUID id) {
        GetProductById getProductById = GetProductById.builder().id(id).build();
        return ResponseEntity.of(Optional.of(ProductResponse.from(getProductByIdUseCaseHandler.handle(getProductById))));
    }


    @GetMapping
    public ResponseEntity<Map<UUID, ProductResponse>> getProductByIdSet(@RequestParam Set<UUID> uuids) {
        ProductIdSetRequest productIdSetRequest = ProductIdSetRequest.builder().ids(uuids).build();
        Map<UUID, ProductResponse> productResponses = getProductByIdSetUseCaseHandler.handle(productIdSetRequest.toModel()).values().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toMap(ProductResponse::getId, Function.identity()));
        return ResponseEntity.of(Optional.of(productResponses));
    }

    @PostMapping
    public ResponseEntity<UUID> saveProduct(@Valid @RequestBody SaveProductRequest request) {
        return ResponseEntity.of(Optional.of(saveProductUseCaseHandler.handle(request.toModel())));
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<UUID> saveProductOption(@PathVariable UUID productId,
                                                  @Valid @RequestBody final SaveProductOptionRequest request) {
        return ResponseEntity.of(Optional.of(saveProductOptionUseCaseHandler.handle(request.toModel(productId))));
    }

}