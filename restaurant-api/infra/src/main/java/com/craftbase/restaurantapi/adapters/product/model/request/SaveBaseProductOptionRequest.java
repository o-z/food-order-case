package com.craftbase.restaurantapi.adapters.product.model.request;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBaseProductOptionRequest implements Serializable {

    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @Positive
    private BigDecimal defaultPrice;
    private PriceType defaultPriceType;
    private ProductStatus status;

    public SaveBaseProductOption toModel(UUID baseProductId) {
        return SaveBaseProductOption.builder()
                .name(name)
                .desc(desc)
                .defaultPrice(defaultPrice)
                .defaultPriceType(defaultPriceType)
                .baseProductId(baseProductId)
                .status(status)
                .build();
    }
}
