package com.craftbase.orderapi.adapters.order.model.response;

import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {

    private UUID id;
    private BigDecimal price;
    private UUID userId;
    private UUID userAddressId;
    private UUID restaurantFranchisingId;
    private UUID basketId;
    private String externalPaymentTransactionId;
    private GatewayType gatewayType;
    private List<OrderProductResponse> orderProducts;
    private int retryCount;
    private OrderStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static OrderResponse from(OrderDto orderDto) {
        return OrderResponse.builder()
                .id(orderDto.getId())
                .price(orderDto.getPrice())
                .userId(orderDto.getUserId())
                .userAddressId(orderDto.getUserAddressId())
                .restaurantFranchisingId(orderDto.getRestaurantFranchisingId())
                .basketId(orderDto.getBasketId())
                .externalPaymentTransactionId(orderDto.getExternalPaymentTransactionId())
                .gatewayType(orderDto.getGatewayType())
                .orderProducts(!CollectionUtils.isEmpty(orderDto.getOrderProducts()) ?
                        orderDto.getOrderProducts().stream()
                                .map(OrderProductResponse::from)
                                .toList() : null)
                .retryCount(orderDto.getRetryCount())
                .status(orderDto.getStatus())
                .createDate(orderDto.getCreateDate())
                .updateDate(orderDto.getUpdateDate())
                .build();
    }
}
