package com.craftbase.restaurantapi.adapters.product;

import com.craftbase.restaurantapi.adapters.product.jpa.BaseProductDataAdapter;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.craftbase.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaseProductDataAdapterTest {

    @Mock
    private BaseProductRepository baseProductRepository;

    @InjectMocks
    private BaseProductDataAdapter baseProductDataAdapter;

    @Test
    public void testSaveProduct_WhenIsNotError_ReturnProductId() {
        SaveBaseProduct saveBaseProduct = SaveBaseProduct.builder()
                .name("Product Name")
                .desc("Product Description")
                .imageUrl("https://example.com/product-image.jpg")
                .defaultPrice(BigDecimal.valueOf(10.99))
                .defaultPriceType(PriceType.TRY)
                .categoryId(UUID.randomUUID())
                .restaurantId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .build();
        BaseProductEntity savedEntity = BaseProductEntity.builder()
                .id(UUID.randomUUID())
                .name(saveBaseProduct.getName())
                .desc(saveBaseProduct.getDesc())
                .imageUrl(saveBaseProduct.getImageUrl())
                .defaultPrice(saveBaseProduct.getDefaultPrice())
                .defaultPriceType(saveBaseProduct.getDefaultPriceType())
                .categoryId(saveBaseProduct.getCategoryId())
                .restaurantId(saveBaseProduct.getRestaurantId())
                .status(saveBaseProduct.getStatus())
                .build();
        when(baseProductRepository.save(Mockito.any(BaseProductEntity.class))).thenReturn(savedEntity);

        UUID result = baseProductDataAdapter.saveBaseProduct(saveBaseProduct);

        assertNotNull(result);
        assertEquals(savedEntity.getId(), result);
    }

    @Test
    public void testGetBaseProductById_WhenIsExist_ReturnBaseProductDto() {
        UUID uuid = UUID.fromString("3ce8e42b-00cf-46b4-86f9-e969f106d39e");
        BaseProductEntity baseProductEntity = BaseProductEntity.builder()
                .id(UUID.fromString("3ce8e42b-00cf-46b4-86f9-e969f106d39e"))
                .name("Big Mac")
                .desc("Big Mac")
                .imageUrl("Big Mac")
                .defaultPrice(new BigDecimal("70.00"))
                .defaultPriceType(PriceType.TRY)
                .categoryId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .restaurantId(UUID.fromString("0ec13e48-4c4e-479c-bb73-de8b1a557c97"))
                .status(ProductStatus.ACTIVE)
                .baseProductOptionEntities(List.of(BaseProductOptionEntity.builder()
                        .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                        .name("Exstra Cheddar")
                        .desc("Exstra Cheddar")
                        .defaultPrice(new BigDecimal("10.00"))
                        .defaultPriceType(PriceType.TRY)
                        .status(ProductStatus.ACTIVE)
                        .build()))
                .productEntities(List.of(ProductEntity.builder()
                        .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                        .name("Exstra Cheddar")
                        .desc("Exstra Cheddar")
                        .price(new BigDecimal("10.00"))
                        .priceType(PriceType.TRY)
                        .status(ProductStatus.ACTIVE)
                        .build()))
                .build();
        when(baseProductRepository.findById(uuid)).thenReturn(Optional.of(baseProductEntity));

        BaseProductDto result = baseProductDataAdapter.getBaseProductById(baseProductEntity.getId());

        assertNotNull(result);
        assertEquals(baseProductEntity.getId(), result.getId());
        assertEquals(baseProductEntity.getName(), result.getName());
        assertEquals(baseProductEntity.getDesc(), result.getDesc());
        assertEquals(baseProductEntity.getImageUrl(), result.getImageUrl());
        assertEquals(baseProductEntity.getDefaultPrice(), result.getDefaultPrice());
        assertEquals(baseProductEntity.getDefaultPriceType(), result.getDefaultPriceType());
        assertEquals(baseProductEntity.getCategoryId(), result.getCategoryId());
        assertEquals(baseProductEntity.getRestaurantId(), result.getRestaurantId());
        assertEquals(baseProductEntity.getStatus(), result.getStatus());
    }
}