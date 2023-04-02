package com.craftbase.orderapi.adapters.basket.jpa.entity;

import com.craftbase.orderapi.basket.model.dto.BasketDto;
import com.craftbase.orderapi.common.entity.BaseEntity;
import com.craftbase.orderapi.common.model.enums.PriceType;
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
@Table(name = "BASKET", schema = "CRAFT_GATE_ORDER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BasketEntity extends BaseEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICE_TYPE")
    private PriceType priceType;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "USER_ID")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "USER_ADDRESS_ID")
    @Type(type = "uuid-char")
    private UUID userAddressId;

    @OneToMany(mappedBy = "basketEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketProductEntity> basketProductEntities;

    public BasketDto toModel() {
        return BasketDto.builder()
                .id(super.getId())
                .priceType(priceType)
                .comment(comment)
                .userId(userId)
                .userAddressId(userAddressId)
                .basketProducts(!CollectionUtils.isEmpty(basketProductEntities) ? basketProductEntities.stream()
                        .map(BasketProductEntity::toModel)
                        .toList() : null)
                .build();
    }
}
