package com.craftbase.orderapi.adapters.order.model.request;

import com.craftbase.orderapi.order.usecase.GetOrdersByPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

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