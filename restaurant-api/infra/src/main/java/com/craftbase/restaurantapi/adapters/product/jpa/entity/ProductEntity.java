package com.craftbase.restaurantapi.adapters.product.jpa.entity;

import com.craftbase.restaurantapi.common.entity.BaseEntity;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.ProductDto;
import com.craftbase.restaurantapi.product.model.dto.ProductOptionDto;
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
import java.util.function.Function;
import java.util.stream.Collectors;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT", schema = "CRAFT_GATE_RESTAURANT")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICE_TYPE")
    private PriceType priceType;

    @Column(name = "RESTAURANT_FRANCHISING_ID")
    @Type(type = "uuid-char")
    private UUID restaurantFranchisingId;

    @Column(name = "CATEGORY_ID")
    @Type(type = "uuid-char")
    private UUID categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASE_PRODUCT_ID", referencedColumnName = "ID")
    private BaseProductEntity baseProductEntity;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductOptionEntity> productOptionEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ProductStatus status;

    public ProductDto toModel() {
        return ProductDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .categoryId(categoryId)
                .restaurantFranchisingId((restaurantFranchisingId))
                .price(price)
                .priceType(priceType)
                .productOptionDtoMap(!CollectionUtils.isEmpty(productOptionEntities) ?
                        productOptionEntities.stream()
                                .map(ProductOptionEntity::toModel)
                                .collect(Collectors.toMap(ProductOptionDto::getId, Function.identity())) : null)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
