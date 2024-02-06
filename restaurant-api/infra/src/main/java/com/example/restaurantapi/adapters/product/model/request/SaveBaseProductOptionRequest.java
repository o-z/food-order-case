package com.example.restaurantapi.adapters.product.model.request;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveBaseProductOption;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
