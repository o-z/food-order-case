package com.craftbase.orderapi.adapters.product.model.dto;

import com.craftbase.orderapi.common.model.enums.PriceType;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import com.craftbase.orderapi.product.model.dto.ProductOptionDto;
import com.craftbase.orderapi.product.model.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdapterDto implements Serializable {

    private UUID id;
    private String name;
    private String desc;
    private String imageUrl;
    private UUID categoryId;
    private UUID restaurantFranchisingId;
    private BigDecimal price;
    private PriceType priceType;
    private ProductStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Map<UUID, ProductOptionAdapterDto> productOptionMap;


    public ProductDto toModel() {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .desc(desc)
                .imageUrl(imageUrl)
                .categoryId(categoryId)
                .restaurantFranchisingId(restaurantFranchisingId)
                .price(price)
                .priceType(priceType)
                .status(status)
                .createDate(createDate)
                .updateDate(updateDate)
                .productOptionDtoMap(!CollectionUtils.isEmpty(productOptionMap) ? productOptionMap.values().stream()
                        .map(ProductOptionAdapterDto::toModel)
                        .collect(Collectors.toMap(ProductOptionDto::getId, Function.identity())) : null)
                .build();
    }
}
