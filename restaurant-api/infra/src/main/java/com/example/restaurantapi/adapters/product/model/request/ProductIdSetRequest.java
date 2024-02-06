package com.example.restaurantapi.adapters.product.model.request;

import com.example.restaurantapi.product.usecase.GetProductByIdSet;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductIdSetRequest implements Serializable {

    private Set<UUID> ids;

    public GetProductByIdSet toModel() {
        return GetProductByIdSet.builder()
                .ids(ids)
                .build();
    }
}
