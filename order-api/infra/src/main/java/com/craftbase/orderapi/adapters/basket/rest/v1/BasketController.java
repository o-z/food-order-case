package com.craftbase.orderapi.adapters.basket.rest.v1;

import com.craftbase.orderapi.adapters.basket.model.request.AddProductToBasketRequest;
import com.craftbase.orderapi.basket.usecase.AddProductToBasket;
import com.craftbase.orderapi.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/v1/baskets")
@RequiredArgsConstructor
@Slf4j
public class BasketController {

    private final VoidUseCaseHandler<AddProductToBasket> addProductToBasketUseCaseHandler;


    @PutMapping("/{basketId}/add-product")
    public ResponseEntity<Void> addProduct(@PathVariable final UUID basketId, @Valid @RequestBody AddProductToBasketRequest request) {
        addProductToBasketUseCaseHandler.handle(request.toModel(basketId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}