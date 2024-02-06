package com.example.restaurantapi.adapters.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.restaurantapi.adapters.product.jpa.BaseProductOptionDataAdapter;
import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductOptionEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductOptionRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveBaseProductOption;
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
public class BaseProductOptionDataAdapterTest {

    @Mock
    private BaseProductOptionRepository baseProductOptionRepository;

    @Mock
    private BaseProductRepository baseProductRepository;

    @InjectMocks
    private BaseProductOptionDataAdapter baseProductOptionDataAdapter;
    @Test
    public void testBaseProductOption_WhenIsNotError_ReturnProductId() {
        UUID baseProductId = UUID.randomUUID();
        SaveBaseProductOption saveBaseProductOption = SaveBaseProductOption.builder()
                .name("Product Option Name")
                .desc("Product Option Description")
                .defaultPrice(BigDecimal.valueOf(10.99))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .baseProductId(baseProductId)
                .build();
        BaseProductOptionEntity savedEntity = BaseProductOptionEntity.builder()
                .id(baseProductId)
                .name(saveBaseProductOption.getName())
                .desc(saveBaseProductOption.getDesc())
                .defaultPrice(saveBaseProductOption.getDefaultPrice())
                .defaultPriceType(saveBaseProductOption.getDefaultPriceType())
                .status(saveBaseProductOption.getStatus())
                .build();

        BaseProductEntity baseProductEntity = BaseProductEntity.builder()
                .id(baseProductId)
                .name("Product Option Name")
                .desc("Product Option Description")
                .defaultPrice(BigDecimal.valueOf(10.99))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        when(baseProductOptionRepository.save(Mockito.any(BaseProductOptionEntity.class))).thenReturn(savedEntity);
        when(baseProductRepository.findById(baseProductId)).thenReturn(Optional.of(baseProductEntity));

        UUID result = baseProductOptionDataAdapter.saveBaseProductOption(saveBaseProductOption);

        assertNotNull(result);
        assertEquals(savedEntity.getId(), result);
    }

    @Test
    public void testGetBaseProductOptionById_WhenIsExist_ReturnBaseProductOptionDto() {
        BaseProductOptionEntity baseProductOptionEntity = BaseProductOptionEntity.builder()
                .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .name("Exstra Cheddar")
                .desc("Exstra Cheddar")
                .defaultPrice(new BigDecimal("10.00"))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        when(baseProductOptionRepository.findById(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992")))
                .thenReturn(Optional.of(baseProductOptionEntity));

        BaseProductOptionDto result = baseProductOptionDataAdapter.getBaseProductOptionById(baseProductOptionEntity.getId());

        assertNotNull(result);
        assertEquals(baseProductOptionEntity.getId(), result.getId());
        assertEquals(baseProductOptionEntity.getName(), result.getName());
        assertEquals(baseProductOptionEntity.getDesc(), result.getDesc());
        assertEquals(baseProductOptionEntity.getDefaultPrice(), result.getDefaultPrice());
        assertEquals(baseProductOptionEntity.getDefaultPriceType(), result.getDefaultPriceType());
        assertEquals(baseProductOptionEntity.getStatus(), result.getStatus());
    }
}