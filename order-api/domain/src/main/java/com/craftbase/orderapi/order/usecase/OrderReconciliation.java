package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.common.usecase.UseCase;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class OrderReconciliation implements UseCase {
    private Set<UUID> orderIds;
}
