package com.example.orderapi.adapters.order.jpa.entity;

import com.example.orderapi.common.entity.BaseEntity;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.order.model.dto.OrderDto;
import com.example.orderapi.order.model.enums.OrderStatus;
import com.example.orderapi.payment.model.enums.GatewayType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PLACE_ORDER", schema = "FOOD_ORDER_CASE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends BaseEntity implements Serializable {

    @Column(name = "PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICE_TYPE")
    private PriceType priceType;

    @Column(name = "USER_ID")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "USER_ADDRESS_ID")
    @Type(type = "uuid-char")
    private UUID userAddressId;

    @Column(name = "RESTAURANT_FRANCHISING_ID")
    @Type(type = "uuid-char")
    private UUID restaurantFranchisingId;

    @Column(name = "BASKET_ID")
    @Type(type = "uuid-char")
    private UUID basketId;

    @Column(name = "EXTERNAL_PAYMENT_TRANSACTION_ID")
    private String externalPaymentTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "GATEWAY_TYPE")
    private GatewayType gatewayType;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductEntity> orderProductEntities;

    @Column(name = "RETRY_COUNT", columnDefinition = "int default 0")
    private int retryCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status;

    public OrderDto toModel() {
        return OrderDto.builder()
                .id(super.getId())
                .price(price)
                .priceType(priceType)
                .userId(userId)
                .userAddressId(userAddressId)
                .restaurantFranchisingId(restaurantFranchisingId)
                .basketId(basketId)
                .externalPaymentTransactionId(externalPaymentTransactionId)
                .gatewayType(gatewayType)
                .orderProducts(!CollectionUtils.isEmpty(orderProductEntities) ? orderProductEntities.stream()
                        .map(OrderProductEntity::toModel).toList() : null)
                .retryCount(retryCount)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }

}
