package com.example.orderapi.adapters;

import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.product.model.dto.ProductDto;
import com.example.orderapi.product.model.dto.ProductOptionDto;
import com.example.orderapi.product.model.enums.ProductStatus;
import com.example.orderapi.product.port.ProductPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class ProductFakeAdapter implements ProductPort {


    @Override
    public ProductDto getProductById(UUID id) {
        Map<UUID, ProductDto> products = new HashMap<>();
        ProductDto product1 = ProductDto.builder()
                .id(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                .name("Cheeseburger")
                .desc("A classic burger with melted cheese")
                .imageUrl("https://example.com/cheeseburger.jpg")
                .categoryId(UUID.randomUUID())
                .restaurantFranchisingId(UUID.randomUUID())
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .productOptionDtoMap(new HashMap<>())
                .build();

        // sample product option data
        ProductOptionDto option1 = ProductOptionDto.builder()
                .id(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"))
                .name("Extra Cheese")
                .desc("Add extra cheese to your burger")
                .price(BigDecimal.valueOf(5.0))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        product1.getProductOptionDtoMap().put(option1.getId(), option1);

        // add products and options to the map
        products.put(product1.getId(), product1);
        return products.get(id);
    }

    @Override
    public Map<UUID, ProductDto> getProductByIdSet(Set<UUID> ids) {
        Map<UUID, ProductDto> result = new HashMap<>();
        for (UUID id : ids) {
            result.put(id, getProductById(id));
        }
        return result;
    }
}