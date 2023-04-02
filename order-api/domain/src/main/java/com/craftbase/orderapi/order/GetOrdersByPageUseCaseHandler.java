package com.craftbase.orderapi.order;

import com.craftbase.orderapi.common.model.dto.PageDto;
import com.craftbase.orderapi.common.usecase.UseCaseHandler;
import com.craftbase.orderapi.common.util.DomainComponent;
import com.craftbase.orderapi.order.model.dto.OrderDto;
import com.craftbase.orderapi.order.port.OrderPort;
import com.craftbase.orderapi.order.usecase.GetOrdersByPage;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetOrdersByPageUseCaseHandler implements UseCaseHandler<PageDto<OrderDto>, GetOrdersByPage> {
    private final OrderPort orderPort;

    @Override
    public PageDto<OrderDto> handle(GetOrdersByPage getOrdersByPage) {
        return orderPort.getOrdersByPage(getOrdersByPage);
    }
}
