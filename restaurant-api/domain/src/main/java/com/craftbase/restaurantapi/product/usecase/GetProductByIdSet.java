package com.craftbase.restaurantapi.product.usecase;

import com.craftbase.restaurantapi.common.usecase.UseCase;
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
public class GetProductByIdSet implements UseCase, Serializable {
    private Set<UUID> ids;
}
