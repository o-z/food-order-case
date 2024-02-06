package com.example.orderapi.order;

import com.example.orderapi.common.model.dto.PageDto;
import com.example.orderapi.common.usecase.UseCaseHandler;
import com.example.orderapi.common.util.DomainComponent;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.port.OrderPort;
import com.example.orderapi.order.usecase.GetOrdersByPage;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetOrdersByPageUseCaseHandler implements
    UseCaseHandler<PageDto<OrderDto>, GetOrdersByPage> {
    private final OrderPort orderPort;

    @Override
    public PageDto<OrderDto> handle(GetOrdersByPage getOrdersByPage) {
        return orderPort.getOrdersByPage(getOrdersByPage);
    }
}
