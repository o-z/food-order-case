package com.craftbase.orderapi.adapters.basket.jpa.entity;

import com.craftbase.orderapi.basket.model.dto.BasketProductDto;
import com.craftbase.orderapi.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASKET_PRODUCT", schema = "CRAFT_GATE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BasketProductEntity extends BaseEntity implements Serializable {

    @Column(name = "PRODUCT_ID")
    @Type(type = "uuid-char")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASKET_ID", referencedColumnName = "ID")
    private BasketEntity basketEntity;

    @OneToMany(mappedBy = "basketProductEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketProductOptionEntity> basketProductOptionEntities;

    public BasketProductDto toModel() {
        return BasketProductDto.builder()
                .id(super.getId())
                .productId(productId)
                .basketProductOptions(!CollectionUtils.isEmpty(basketProductOptionEntities) ? basketProductOptionEntities.stream()
                        .map(BasketProductOptionEntity::toModel).toList() : null)
                .build();
    }
}
