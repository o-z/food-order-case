package com.craftbase.restaurantapi.adapters.product.rest.v1;

import com.craftbase.restaurantapi.adapters.product.model.request.SaveBaseProductOptionRequest;
import com.craftbase.restaurantapi.adapters.product.model.request.SaveBaseProductRequest;
import com.craftbase.restaurantapi.common.usecase.UseCaseHandler;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProduct;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/v1/base-products")
@RequiredArgsConstructor
@Slf4j
public class BaseProductController {

    private final UseCaseHandler<UUID, SaveBaseProduct> saveBaseProductUseCaseHandler;
    private final UseCaseHandler<UUID, SaveBaseProductOption> saveBaseProductOptionUseCaseHandler;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> saveBaseProduct(@Valid @RequestBody final SaveBaseProductRequest request) {
        return ResponseEntity.of(Optional.of(saveBaseProductUseCaseHandler.handle(request.toModel())));
    }

    @PostMapping("/{baseProductId}/base-options")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> saveBaseProductOption(@PathVariable UUID baseProductId,
                                                      @Valid @RequestBody final SaveBaseProductOptionRequest request) {
        return ResponseEntity.of(Optional.of(saveBaseProductOptionUseCaseHandler.handle(request.toModel(baseProductId))));
    }

}