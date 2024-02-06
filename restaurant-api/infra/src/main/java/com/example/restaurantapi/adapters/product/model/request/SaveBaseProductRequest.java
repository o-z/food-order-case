package com.example.restaurantapi.adapters.product.model.request;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveBaseProductRequest implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String imageUrl;
    @Positive
    private BigDecimal defaultPrice;
    private PriceType defaultPriceType;
    @NotNull
    private UUID categoryId;
    @NotNull
    private UUID restaurantId;
    private ProductStatus status;

    public SaveBaseProduct toModel() {
        return SaveBaseProduct.builder()
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .defaultPrice(defaultPrice)
                .defaultPriceType(defaultPriceType)
                .categoryId(categoryId)
                .restaurantId(restaurantId)
                .status(status)
                .build();
    }
}
