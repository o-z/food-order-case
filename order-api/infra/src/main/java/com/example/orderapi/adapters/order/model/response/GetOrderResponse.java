package com.example.orderapi.adapters.order.model.response;

import com.example.orderapi.common.model.dto.PageDto;
import com.example.orderapi.order.model.dto.OrderDto;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse implements Serializable {
    private List<OrderResponse> orders;
    private Integer page;
    private Integer size;
    private Integer totalPage;

    public static GetOrderResponse from(PageDto<OrderDto> orderDtoPage) {
        return GetOrderResponse.builder()
                .orders(!CollectionUtils.isEmpty(orderDtoPage.getContents()) ?
                        orderDtoPage.getContents().stream()
                                .map(OrderResponse::from).toList() : null)
                .page(orderDtoPage.getPage())
                .size(orderDtoPage.getSize())
                .totalPage(orderDtoPage.getTotalPage())
                .build();
    }
}
