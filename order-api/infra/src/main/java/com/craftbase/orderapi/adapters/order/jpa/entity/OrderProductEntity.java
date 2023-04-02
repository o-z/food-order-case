package com.craftbase.orderapi.adapters.order.jpa.entity;

import com.craftbase.orderapi.common.entity.BaseEntity;
import com.craftbase.orderapi.order.model.dto.OrderProductDto;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PLACE_ORDER_PRODUCT", schema = "CRAFT_GATE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class OrderProductEntity extends BaseEntity implements Serializable {

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "PRODUCT_ID")
    @Type(type = "uuid-char")
    private UUID productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    private OrderEntity orderEntity;

    @OneToMany(mappedBy = "orderProductEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductOptionEntity> orderProductOptionEntities;

    public OrderProductDto toModel() {
        return OrderProductDto.builder()
                .id(super.getId())
                .price(price)
                .productId(productId)
                .name(productName)
                .orderProductOptions(!CollectionUtils.isEmpty(orderProductOptionEntities) ? orderProductOptionEntities.stream()
                        .map(OrderProductOptionEntity::toModel).toList() : null)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
