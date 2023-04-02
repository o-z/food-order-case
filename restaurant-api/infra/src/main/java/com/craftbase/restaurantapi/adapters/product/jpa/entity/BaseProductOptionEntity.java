package com.craftbase.restaurantapi.adapters.product.jpa.entity;

import com.craftbase.restaurantapi.common.entity.BaseEntity;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASE_PRODUCT_OPTION", schema = "CRAFT_GATE_RESTAURANT")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BaseProductOptionEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "DEFAULT_PRICE")
    private BigDecimal defaultPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_PRICE_TYPE")
    private PriceType defaultPriceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_PRODUCT_ID", referencedColumnName = "ID")
    private BaseProductEntity baseProductEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus status;

    public BaseProductOptionDto toModel() {
        return BaseProductOptionDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .defaultPrice(defaultPrice)
                .defaultPriceType(defaultPriceType)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
