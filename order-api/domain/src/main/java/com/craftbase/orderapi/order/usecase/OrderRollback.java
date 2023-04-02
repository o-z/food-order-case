package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.common.usecase.UseCase;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class OrderRollback implements UseCase, Serializable {
    private UUID orderId;
}
