package com.craftbase.restaurantapi.adapters.product.model.request;

import com.craftbase.restaurantapi.product.usecase.GetProductByIdSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

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
