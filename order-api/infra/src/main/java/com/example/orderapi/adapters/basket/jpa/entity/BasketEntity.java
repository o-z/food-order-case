package com.example.orderapi.adapters.basket.jpa.entity;

import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.common.entity.BaseEntity;
import com.example.orderapi.common.model.enums.PriceType;
import java.io.Serializable;
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
@Table(name = "BASKET", schema = "FOOD_ORDER_CASE_ORDER")
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
