package com.example.orderapi.adapters.order.model.response;

import com.example.orderapi.order.model.dto.OrderProductOptionDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductOptionResponse implements Serializable {

    private UUID id;
    private BigDecimal price;
    private UUID productOptionId;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static OrderProductOptionResponse from(OrderProductOptionDto orderProductOptionDto) {

        return OrderProductOptionResponse.builder()
                .id(orderProductOptionDto.getId())
                .price(orderProductOptionDto.getPrice())
                .productOptionId(orderProductOptionDto.getProductOptionId())
                .name(orderProductOptionDto.getName())
                .createDate(orderProductOptionDto.getCreateDate())
                .updateDate(orderProductOptionDto.getUpdateDate())
                .build();
    }
}
