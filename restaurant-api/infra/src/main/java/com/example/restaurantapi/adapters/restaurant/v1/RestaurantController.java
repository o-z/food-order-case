package com.example.restaurantapi.adapters.restaurant.v1;

import com.example.restaurantapi.adapters.restaurant.model.request.SaveRestaurantFranchisingRequest;
import com.example.restaurantapi.adapters.restaurant.model.request.SaveRestaurantRequest;
import com.example.restaurantapi.adapters.restaurant.model.response.RestaurantResponse;
import com.example.restaurantapi.common.usecase.UseCaseHandler;
import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.usecase.GetRestaurantById;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final UseCaseHandler<RestaurantDto, GetRestaurantById> getRestaurantByIdUseCaseHandler;
    private final UseCaseHandler<UUID, SaveRestaurant> saveRestaurantUseCaseHandler;
    private final UseCaseHandler<UUID, SaveRestaurantFranchising> saveRestaurantFranchisingUseCaseHandler;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable final UUID restaurantId) {
        GetRestaurantById getRestaurantById = GetRestaurantById.builder().restaurantId(restaurantId).build();
        return ResponseEntity.of(Optional.of(RestaurantResponse.from(getRestaurantByIdUseCaseHandler.handle(getRestaurantById))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> saveRestaurant(@Valid @RequestBody SaveRestaurantRequest request) {
        return ResponseEntity.of(Optional.of(saveRestaurantUseCaseHandler.handle(request.toModel())));
    }

    @PostMapping("/{restaurantId}/franchisings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> saveRestaurantFranchising(@PathVariable final UUID restaurantId,
                                                          @Valid @RequestBody SaveRestaurantFranchisingRequest request) {
        return ResponseEntity.of(Optional.of(saveRestaurantFranchisingUseCaseHandler.handle(request.toModel(restaurantId))));
    }

}