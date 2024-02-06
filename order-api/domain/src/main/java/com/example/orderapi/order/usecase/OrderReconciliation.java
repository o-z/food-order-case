package com.example.orderapi.order.usecase;

import com.example.orderapi.common.usecase.UseCase;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderReconciliation implements UseCase {
    private Set<UUID> orderIds;
}
