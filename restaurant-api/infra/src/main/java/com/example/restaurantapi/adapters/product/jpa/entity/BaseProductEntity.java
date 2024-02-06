package com.example.restaurantapi.adapters.product.jpa.entity;

import com.example.restaurantapi.common.entity.BaseEntity;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.BaseProductDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "BASE_PRODUCT", schema = "FOOD_ORDER_CASE_RESTAURANT")
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
