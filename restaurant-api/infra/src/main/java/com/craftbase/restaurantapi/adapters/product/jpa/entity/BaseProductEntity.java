package com.craftbase.restaurantapi.adapters.product.jpa.entity;

import com.craftbase.restaurantapi.common.entity.BaseEntity;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BASE_PRODUCT", schema = "CRAFT_GATE_RESTAURANT")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class BaseProductEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "DEFAULT_PRICE")
    private BigDecimal defaultPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_PRICE_TYPE")
    private PriceType defaultPriceType;

    @Column(name = "CATEGORY_ID")
    @Type(type = "uuid-char")
    private UUID categoryId;

    @Column(name = "RESTAURANT_ID")
    @Type(type = "uuid-char")
    private UUID restaurantId;

    @OneToMany(mappedBy = "baseProductEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productEntities;

    @OneToMany(mappedBy = "baseProductEntity", cascade = CascadeType.ALL)
    private List<BaseProductOptionEntity> baseProductOptionEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus status;

    public BaseProductDto toModel() {
        return BaseProductDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .defaultPrice(defaultPrice)
                .defaultPriceType(defaultPriceType)
                .imageUrl(imageUrl)
                .categoryId(categoryId)
                .restaurantId(restaurantId)
                .status(status)
                .products(!CollectionUtils.isEmpty(productEntities) ?
                        productEntities.stream()
                                .map(ProductEntity::toModel)
                                .toList() : null)
                .baseProductOptions(!CollectionUtils.isEmpty(baseProductOptionEntities) ?
                        baseProductOptionEntities.stream()
                                .map(BaseProductOptionEntity::toModel).toList() : null)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
