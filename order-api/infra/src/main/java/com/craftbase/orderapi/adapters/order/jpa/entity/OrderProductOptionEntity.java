package com.craftbase.orderapi.adapters.order.jpa.entity;

import com.craftbase.orderapi.common.entity.BaseEntity;
import com.craftbase.orderapi.order.model.dto.OrderProductOptionDto;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PLACE_ORDER_PRODUCT_OPTION", schema = "CRAFT_GATE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class OrderProductOptionEntity extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID", referencedColumnName = "ID")
    private OrderProductEntity orderProductEntity;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "PRODUCT_OPTION_ID")
    @Type(type = "uuid-char")
    private UUID productOptionId;

    @Column(name = "PRODUCT_OPTION_NAME")
    private String productOptionName;

    public OrderProductOptionDto toModel() {
        return OrderProductOptionDto.builder()
                .id(super.getId())
                .price(price)
                .productOptionId(productOptionId)
                .name(productOptionName)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
