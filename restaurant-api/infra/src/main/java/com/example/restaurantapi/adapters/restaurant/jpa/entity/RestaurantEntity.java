package com.example.restaurantapi.adapters.restaurant.jpa.entity;

import com.example.restaurantapi.common.entity.BaseEntity;
import com.example.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import java.io.Serializable;
import java.util.List;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTAURANT", schema = "FOOD_ORDER_CASE_RESTAURANT")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class RestaurantEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToMany(mappedBy = "restaurantEntity", cascade = CascadeType.ALL)
    private List<RestaurantFranchisingEntity> restaurantFranchisingEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private RestaurantStatus status;

    public RestaurantDto toModel() {
        return RestaurantDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .restaurantFranchisings(!CollectionUtils.isEmpty(restaurantFranchisingEntities) ? restaurantFranchisingEntities.stream()
                        .map(RestaurantFranchisingEntity::toModel)
                        .toList() : null)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
