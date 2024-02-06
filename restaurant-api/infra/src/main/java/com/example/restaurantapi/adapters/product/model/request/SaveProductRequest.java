package com.example.restaurantapi.adapters.product.model.request;

import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveProduct;
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
public class SaveProductRequest implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String imageUrl;
    @Positive
    private BigDecimal price;
    private PriceType priceType;
    @NotNull
    private UUID restaurantFranchisingId;
    @NotNull
    private UUID categoryId;
    @NotNull
    private UUID baseProductId;
    private ProductStatus status;

    public SaveProduct toModel() {
        return SaveProduct.builder()
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .price(price)
                .priceType(priceType)
                .restaurantFranchisingId(restaurantFranchisingId)
                .categoryId(categoryId)
                .baseProductId(baseProductId)
                .status(status)
                .build();
    }
}
