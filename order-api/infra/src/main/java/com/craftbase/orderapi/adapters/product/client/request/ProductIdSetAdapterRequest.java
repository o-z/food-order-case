package com.craftbase.orderapi.adapters.product.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductIdSetAdapterRequest {
    @NotEmpty
    @Size(min = 1, max = 50)
    private Set<UUID> ids;
}
