package com.craftbase.orderapi.adapters.basket.jpa.entity;

import com.craftbase.orderapi.basket.model.dto.BasketProductOptionDto;
import com.craftbase.orderapi.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASKET_PRODUCT_OPTION", schema = "CRAFT_GATE_ORDER")
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
