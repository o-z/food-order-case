package com.example.orderapi.order.usecase;

import com.example.orderapi.common.usecase.UseCase;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Builder
public class OrderRollback implements UseCase, Serializable {
    private UUID orderId;
}
