package com.craftbase.orderapi.order.usecase;

import com.craftbase.orderapi.common.model.dto.Pagination;
import com.craftbase.orderapi.common.usecase.UseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetOrdersByPage extends Pagination implements UseCase, Serializable {
    private UUID userId;

}
