package com.example.orderapi.adapters.order.model.request;

import com.example.orderapi.order.usecase.GetOrdersByPage;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersRequest implements Serializable {
    private UUID userId;
    private Integer page;
    private Integer size;

    public GetOrdersByPage toModel() {
        return GetOrdersByPage.builder()
                .userId(userId)
                .page(page)
                .size(size)
                .build();
    }

}