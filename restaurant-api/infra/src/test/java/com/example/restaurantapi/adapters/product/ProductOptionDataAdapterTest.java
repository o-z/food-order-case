package com.example.restaurantapi.adapters.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.restaurantapi.adapters.product.jpa.ProductOptionDataAdapter;
import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveProductOption;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductOptionDataAdapterTest {

    @Mock
    private ProductOptionRepository productOptionRepository;
    @Mock
    private BaseProductOptionRepository baseProductOptionRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductOptionDataAdapter productOptionDataAdapter;

    @Test
    public void testSaveProductOption_WhenIsNotError_ReturnProductId() {

        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .name("Product Name")
                .desc("Product Description")
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        BaseProductOptionEntity baseProductOptionEntity = BaseProductOptionEntity.builder()
                .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .name("Exstra Cheddar")
                .desc("Exstra Cheddar")
                .defaultPrice(new BigDecimal("10.00"))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();

        ProductOptionEntity productOptionEntity = ProductOptionEntity.builder()
                .id(UUID.randomUUID())
                .name("name")
                .desc("desc")
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .productEntity(productEntity)
                .baseProductOptionEntity(baseProductOptionEntity)
                .build();

        SaveProductOption saveProductOption = SaveProductOption.builder()
                .name(productOptionEntity.getName())
                .desc(productOptionEntity.getDesc())
                .price(productOptionEntity.getPrice())
                .priceType(productOptionEntity.getPriceType())
                .status(productOptionEntity.getStatus())
                .productId(productEntity.getId())
                .baseProductOptionId(baseProductOptionEntity.getId())
                .restaurantFranchisingId(UUID.randomUUID())
                .build();
        when(productRepository.findById(productEntity.getId()))
                .thenReturn(Optional.of(productEntity));
        when(baseProductOptionRepository.findById(baseProductOptionEntity.getId()))
                .thenReturn(Optional.of(baseProductOptionEntity));
        when(productOptionRepository.save(Mockito.any(ProductOptionEntity.class)))
                .thenReturn(productOptionEntity);
        UUID result = productOptionDataAdapter.saveProductOption(saveProductOption);

        assertEquals(productOptionEntity.getId(), result);
    }
}
