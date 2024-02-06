package com.example.restaurantapi.adapters.product.jpa.entity;

import com.example.restaurantapi.common.entity.BaseEntity;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASE_PRODUCT_OPTION", schema = "FOOD_ORDER_CASE_RESTAURANT")
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
