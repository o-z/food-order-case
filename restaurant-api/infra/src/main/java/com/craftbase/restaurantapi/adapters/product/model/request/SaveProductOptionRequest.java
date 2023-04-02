package com.craftbase.restaurantapi.adapters.product.model.request;

import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.craftbase.restaurantapi.product.usecase.SaveProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveProductOptionRequest implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @Positive
    private BigDecimal price;
    private PriceType priceType;
    @NotNull
    private UUID restaurantFranchisingId;
    @NotNull
    private UUID baseProductOptionId;
    private ProductStatus status;

    public SaveProductOption toModel(UUID productId) {
        return SaveProductOption.builder()
                .name(name)
                .desc(desc)
                .price(price)
                .priceType(priceType)
                .restaurantFranchisingId(restaurantFranchisingId)
                .productId(productId)
                .baseProductOptionId(baseProductOptionId)
                .status(status)
                .build();
    }
}
