package com.craftbase.orderapi.adapters.order.rest.v1;

import com.craftbase.orderapi.adapters.order.model.request.GetOrdersRequest;
import com.craftbase.orderapi.adapters.order.model.request.PlaceOrderRequest;
import com.craftbase.orderapi.adapters.order.model.response.GetOrderResponse;
import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.usecase.UseCaseHandler;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;
import com.craftbase.orderapi.order.usecase.PlaceOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final UseCaseHandler<UUID, PlaceOrder> placeOrderUseCaseHandler;
    private final UseCaseHandler<PageDto<OrderDto>, GetOrdersByPage> getOrdersByPageUseCaseHandler;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        return ResponseEntity.of(Optional.of(placeOrderUseCaseHandler.handle(request.toModel())));
    }

    @GetMapping("/histories/{userId}")
    public ResponseEntity<GetOrderResponse> getOrders(@PathVariable final UUID userId,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer size) {
        GetOrdersRequest request = GetOrdersRequest.builder().userId(userId).page(page).size(size).build();
        return ResponseEntity.of(Optional.of(GetOrderResponse.from(getOrdersByPageUseCaseHandler.handle(request.toModel()))));
    }

}