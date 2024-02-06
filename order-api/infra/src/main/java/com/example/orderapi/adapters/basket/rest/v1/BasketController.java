package com.example.orderapi.adapters.basket.rest.v1;

import com.example.orderapi.adapters.basket.model.request.AddProductToBasketRequest;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.usecase.VoidUseCaseHandler;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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