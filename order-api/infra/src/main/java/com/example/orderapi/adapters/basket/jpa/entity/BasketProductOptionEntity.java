package com.example.orderapi.adapters.basket.jpa.entity;

import com.example.orderapi.basket.model.dto.BasketProductOptionDto;
import com.example.orderapi.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASKET_PRODUCT_OPTION", schema = "FOOD_ORDER_CASE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BasketProductOptionEntity extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_PRODUCT_ID", referencedColumnName = "ID")
    private BasketProductEntity basketProductEntity;

    @Column(name = "PRODUCT_OPTION_ID")
    @Type(type = "uuid-char")
    private UUID productOptionId;

    public BasketProductOptionDto toModel() {
        return BasketProductOptionDto.builder()
                .id(super.getId())
                .productOptionId(productOptionId)
                .build();
    }
}
