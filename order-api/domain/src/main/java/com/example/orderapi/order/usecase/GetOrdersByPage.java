package com.example.orderapi.order.usecase;

import com.example.orderapi.common.model.dto.Pagination;
import com.example.orderapi.common.usecase.UseCase;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetOrdersByPage extends Pagination implements UseCase, Serializable {
    private UUID userId;

}
