package com.example.restaurantapi.adapters.restaurant.jpa.entity;

import com.example.restaurantapi.common.entity.BaseEntity;
import com.example.restaurantapi.restaurant.model.dto.RestaurantFranchisingDto;
import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RESTAURANT_FRANCHISING", schema = "FOOD_ORDER_CASE_RESTAURANT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "RESTAURANT_ID"})})
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class RestaurantFranchisingEntity extends BaseEntity implements Serializable {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private RestaurantEntity restaurantEntity;

    @Column(name = "RESTAURANT_ID", insertable = false, updatable = false)
    @Type(type = "uuid-char")
    private UUID restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private RestaurantStatus status;

    public RestaurantFranchisingDto toModel() {
        return RestaurantFranchisingDto.builder()
                .id(super.getId())
                .name(name)
                .desc(desc)
                .country(country)
                .address(address)
                .restaurantId(restaurantId)
                .status(status)
                .createDate(super.getCreateDate())
                .updateDate(super.getUpdateDate())
                .build();
    }
}
