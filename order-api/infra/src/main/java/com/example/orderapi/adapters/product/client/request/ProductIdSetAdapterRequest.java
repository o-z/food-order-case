package com.example.orderapi.adapters.product.client.request;

import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductIdSetAdapterRequest {
    @NotEmpty
    @Size(min = 1, max = 50)
    private Set<UUID> ids;
}
