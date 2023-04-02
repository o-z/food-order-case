package com.craftbase.orderapi.adapters.order.model.response;

import com.craftbase.orderapi.order.model.dto.OrderProductDto;
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
public class OrderProductResponse implements Serializable {

    private UUID id;
    private BigDecimal price;
    private UUID productId;
    private String name;
    private List<OrderProductOptionResponse> orderProductOptions;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static OrderProductResponse from(OrderProductDto orderProductDto) {
        return OrderProductResponse.builder()
                .id(orderProductDto.getId())
                .price(orderProductDto.getPrice())
                .productId(orderProductDto.getProductId())
                .name(orderProductDto.getName())
                .orderProductOptions(!CollectionUtils.isEmpty(orderProductDto.getOrderProductOptions()) ?
                        orderProductDto.getOrderProductOptions().stream()
                                .map(OrderProductOptionResponse::from).toList()
                        : null)
                .createDate(orderProductDto.getCreateDate())
                .updateDate(orderProductDto.getUpdateDate())
                .build();
    }
}
