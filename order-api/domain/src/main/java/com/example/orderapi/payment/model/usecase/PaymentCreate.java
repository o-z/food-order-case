package com.example.orderapi.payment.model.usecase;

import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.common.usecase.UseCase;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.payment.model.dto.CreditCard;
import com.example.orderapi.payment.model.dto.PaymentCreateItem;
import com.example.orderapi.payment.model.enums.PaymentItemType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentCreate implements UseCase, Serializable {

    private UUID transactionId;
    private BigDecimal price;
    private PriceType priceType;
    private List<PaymentCreateItem> paymentCreateItems;
    private CreditCard creditCard;

    public static PaymentCreate from(CreditCard creditCard,
                                     OrderDto orderDto) {
        List<PaymentCreateItem> paymentCreateItems = new ArrayList<>();
        setPaymentCreateItems(orderDto, paymentCreateItems);
        return PaymentCreate.builder()
                .transactionId(orderDto.getId())
                .price(orderDto.getPrice())
                .priceType(orderDto.getPriceType())
                .paymentCreateItems(paymentCreateItems)
                .creditCard(creditCard)
                .build();
    }

    private static void setPaymentCreateItems(OrderDto orderDto, List<PaymentCreateItem> paymentCreateItems) {

        orderDto.getOrderProducts().forEach(orderProductDto -> {
            paymentCreateItems.add(
                    generatePaymentCreateItem(PaymentItemType.PRODUCT,
                            orderProductDto.getId(),
                            orderProductDto.getName(),
                            orderProductDto.getPrice()));

            if (orderProductDto.getOrderProductOptions() != null) {
                orderProductDto.getOrderProductOptions().forEach(orderProductOptionDto -> paymentCreateItems.add(
                        generatePaymentCreateItem(PaymentItemType.PRODUCT_OPTION,
                                orderProductOptionDto.getProductOptionId(),
                                orderProductOptionDto.getName(),
                                orderProductOptionDto.getPrice())));
            }

        });
    }

    private static PaymentCreateItem generatePaymentCreateItem(PaymentItemType productOption,
                                                               UUID orderProductOptionDto,
                                                               String orderProductOptionDto1,
                                                               BigDecimal orderProductOptionDto2) {
        return PaymentCreateItem.builder()
                .id(productOption.getSort() + "_" + orderProductOptionDto)
                .name(orderProductOptionDto1)
                .price(orderProductOptionDto2)
                .build();
    }

}
