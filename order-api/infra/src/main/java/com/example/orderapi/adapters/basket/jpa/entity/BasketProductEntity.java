package com.example.orderapi.adapters.basket.jpa.entity;

import com.example.orderapi.basket.model.dto.BasketProductDto;
import com.example.orderapi.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASKET_PRODUCT", schema = "FOOD_ORDER_CASE_ORDER")
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
