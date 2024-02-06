package com.example.orderapi.order.usecase;

import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.common.usecase.UseCase;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.payment.model.enums.GatewayType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrder implements UseCase, Serializable {

    private BigDecimal price;
    private PriceType priceType;
    private UUID userId;
    private UUID userAddressId;
    private UUID restaurantFranchisingId;
    private UUID basketId;
    private GatewayType gatewayType;
    private List<CreateOrderProduct> orderProducts;
    private OrderStatus status;

    public static CreateOrder from(PlaceOrder placeOrder, BasketDto basket, GatewayType gatewayType) {
        return CreateOrder.builder()
                .price(basket.getCalculatedPrice())
                .priceType(basket.getPriceType())
                .userId(basket.getUserId())
                .userAddressId(placeOrder.getUserAddressId())
                .restaurantFranchisingId(placeOrder.getRestaurantFranchisingId())
                .basketId(basket.getId())
                .gatewayType(gatewayType)
                .orderProducts(basket.getBasketProducts().stream()
                        .map(CreateOrderProduct::from)
                        .toList())
                .status(OrderStatus.INITIAL)
                .build();
    }
}
