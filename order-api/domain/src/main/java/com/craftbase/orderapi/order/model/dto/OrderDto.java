package com.craftbase.orderapi.order.model.dto;

import com.craftbase.orderapi.common.model.dto.BaseDto;
import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.order.model.enums.OrderStatus;
import com.craftbase.orderapi.payment.model.enums.GatewayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
