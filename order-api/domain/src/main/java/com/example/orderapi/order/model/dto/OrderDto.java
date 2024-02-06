package com.example.orderapi.order.model.dto;

import com.example.orderapi.common.model.dto.BaseDto;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.payment.model.enums.GatewayType;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends BaseDto {
    private BigDecimal price;
    private PriceType priceType;
    private UUID userId;
    private UUID userAddressId;
    private UUID restaurantFranchisingId;
    private UUID basketId;
    private String externalPaymentTransactionId;
    private GatewayType gatewayType;
    private List<OrderProductDto> orderProducts;
    private int retryCount;
    private OrderStatus status;
}
