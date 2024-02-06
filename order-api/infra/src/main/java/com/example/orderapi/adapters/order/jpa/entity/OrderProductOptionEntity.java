package com.example.orderapi.adapters.order.jpa.entity;

import com.example.orderapi.common.entity.BaseEntity;
import com.example.orderapi.order.model.dto.OrderProductOptionDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PLACE_ORDER_PRODUCT_OPTION", schema = "FOOD_ORDER_CASE_ORDER")
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
