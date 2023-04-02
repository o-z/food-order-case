package com.craftbase.restaurantapi.adapters.restaurant.jpa.entity;

import com.craftbase.restaurantapi.common.entity.BaseEntity;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTAURANT", schema = "CRAFT_GATE_RESTAURANT")
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
