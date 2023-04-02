package com.craftbase.restaurantapi.adapters.product.jpa.entity;

import com.craftbase.restaurantapi.common.entity.BaseEntity;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.ProductOptionDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT_OPTION", schema = "CRAFT_GATE_RESTAURANT")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ProductOptionEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICE_TYPE")
    private PriceType priceType;

    @Column(name = "RESTAURANT_FRANCHISING_ID")
    @Type(type = "uuid-char")
    private UUID restaurantFranchisingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_PRODUCT_OPTION_ID", referencedColumnName = "ID")
    private BaseProductOptionEntity baseProductOptionEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private ProductEntity productEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus status;

    public ProductOptionDto toModel() {
        return ProductOptionDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .restaurantFranchisingId(restaurantFranchisingId)
                .price(price)
                .priceType(priceType)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
