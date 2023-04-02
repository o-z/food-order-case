package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.common.usecase.UseCase;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
