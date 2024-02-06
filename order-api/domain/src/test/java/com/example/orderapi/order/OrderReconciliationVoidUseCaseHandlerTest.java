package com.example.orderapi.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.orderapi.adapters.OrderFakeAdapter;
import com.example.orderapi.adapters.OrderRollbackFakeAdapter;
import com.example.orderapi.adapters.PaymentFakeAdapter;
import com.example.orderapi.common.model.dto.PageDto;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.order.usecase.CreateOrder;
import com.example.orderapi.order.usecase.CreateOrderProduct;
import com.example.orderapi.order.usecase.CreateOrderProductOption;
import com.example.orderapi.order.usecase.GetOrdersByPage;
import com.example.orderapi.order.usecase.OrderReconciliation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderReconciliationVoidUseCaseHandlerTest {

    private final OrderFakeAdapter orderFakeAdapter = new OrderFakeAdapter();
    private final OrderReconciliationVoidUseCaseHandler handler =
            new OrderReconciliationVoidUseCaseHandler(orderFakeAdapter,
                    new PaymentFakeAdapter(),
                    new OrderRollbackFakeAdapter(orderFakeAdapter)
            );
    @Test
    void testOrderReconciliation_WhenOrderRefunded_ReturnNothing() {
        OrderReconciliation orderReconciliation = OrderReconciliation.builder().build();
        CreateOrder createOrder = getCreateOrder();
        orderFakeAdapter.createOrder(createOrder);

        handler.handle(orderReconciliation);
        GetOrdersByPage getOrdersByPage = GetOrdersByPage.builder().page(0).size(10).userId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892")).build();

        PageDto<OrderDto> ordersByPage = orderFakeAdapter.getOrdersByPage(getOrdersByPage);

        assertEquals(OrderStatus.PAYMENT_FAILED, ordersByPage.getContents().get(0).getStatus());
    }


    private CreateOrder getCreateOrder() {
        CreateOrder createOrder = new CreateOrder();
        createOrder.setPrice(BigDecimal.valueOf(60));
        createOrder.setPriceType(PriceType.TRY);
        createOrder.setUserId(UUID.fromString("0298f8c3-ef2c-4378-a008-2595c2d00892"));
        createOrder.setUserAddressId(UUID.fromString("7661ceb1-af39-4db5-b357-99d952ae7e40"));
        createOrder.setRestaurantFranchisingId(UUID.fromString("13bb9115-09aa-4f79-b28a-87a979dffce1"));
        createOrder.setBasketId(UUID.fromString("b4a1e188-99b2-4768-b375-a7a7259c94ab"));
        createOrder.setStatus(OrderStatus.ROLLBACK);

        CreateOrderProduct orderProduct = new CreateOrderProduct();
        orderProduct.setName("Cheeseburger");
        orderProduct.setPrice(BigDecimal.valueOf(50));
        orderProduct.setProductId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"));
        List<CreateOrderProductOption> createOrderProductOptions = new ArrayList<>();
        CreateOrderProductOption createOrderProductOption = new CreateOrderProductOption();
        createOrderProductOption.setName("Extra Cheese");
        createOrderProductOption.setPrice(BigDecimal.valueOf(10));
        createOrderProductOption.setProductOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"));
        createOrderProductOptions.add(createOrderProductOption);
        orderProduct.setOrderProductOptions(createOrderProductOptions);
        List<CreateOrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);
        createOrder.setOrderProducts(orderProducts);
        return createOrder;
    }
}