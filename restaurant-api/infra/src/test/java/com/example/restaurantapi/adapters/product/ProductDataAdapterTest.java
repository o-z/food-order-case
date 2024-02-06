package com.example.restaurantapi.adapters.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.example.restaurantapi.adapters.product.jpa.ProductDataAdapter;
import com.example.restaurantapi.adapters.product.jpa.entity.BaseProductEntity;
import com.example.restaurantapi.adapters.product.jpa.entity.ProductEntity;
import com.example.restaurantapi.adapters.product.jpa.repository.BaseProductRepository;
import com.example.restaurantapi.adapters.product.jpa.repository.ProductRepository;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveProduct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProductDataAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BaseProductRepository baseProductRepository;

    @InjectMocks
    private ProductDataAdapter productDataAdapter;

    @Test
    public void testSaveProduct_WhenIsNotError_ReturnProductId() {
        UUID baseProductId = UUID.randomUUID();
        SaveProduct saveProduct = SaveProduct.builder()
                .name("Product Name")
                .desc("Product Description")
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .baseProductId(baseProductId)
                .build();
        ProductEntity savedEntity = ProductEntity.builder()
                .id(baseProductId)
                .name(saveProduct.getName())
                .desc(saveProduct.getDesc())
                .price(saveProduct.getPrice())
                .priceType(saveProduct.getPriceType())
                .status(saveProduct.getStatus())
                .build();

        BaseProductEntity baseProductEntity = BaseProductEntity.builder()
                .id(baseProductId)
                .name("Base Product Name")
                .desc("Base Product Description")
                .defaultPrice(BigDecimal.valueOf(10.99))
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(savedEntity);
        when(baseProductRepository.findById(baseProductId)).thenReturn(Optional.of(baseProductEntity));

        UUID result = productDataAdapter.saveProduct(saveProduct);

        assertNotNull(result);
        assertEquals(savedEntity.getId(), result);
    }

    @Test
    public void testGetProductById_WhenIsExist_ProductDto() {
        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .name("Product Name")
                .desc("Product Description")
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        when(productRepository.findById(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992")))
                .thenReturn(Optional.of(productEntity));

        ProductDto result = productDataAdapter.getProductById(productEntity.getId());

        assertNotNull(result);
        assertEquals(productEntity.getId(), result.getId());
        assertEquals(productEntity.getName(), result.getName());
        assertEquals(productEntity.getDesc(), result.getDesc());
        assertEquals(productEntity.getPrice(), result.getPrice());
        assertEquals(productEntity.getPriceType(), result.getPriceType());
        assertEquals(productEntity.getStatus(), result.getStatus());
    }

    @Test
    public void testGetProductByIdSet_WhenIsExist_ProductDtoMap() {

        ProductEntity productEntity = ProductEntity.builder()
                .id(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))
                .name("Product Name")
                .desc("Product Description")
                .price(BigDecimal.valueOf(10.99))
                .priceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build();
        when(productRepository.findAllById(Set.of(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"))))
                .thenReturn(List.of(productEntity));

        Map<UUID, ProductDto> resultMap = productDataAdapter.getProductByIdSet(Set.of(productEntity.getId()));
        ProductDto result = resultMap.get(UUID.fromString("e1cf9b88-4697-4ad1-a2f9-090c16f4b992"));
        assertNotNull(result);
        assertEquals(productEntity.getId(), result.getId());
        assertEquals(productEntity.getName(), result.getName());
        assertEquals(productEntity.getDesc(), result.getDesc());
        assertEquals(productEntity.getPrice(), result.getPrice());
        assertEquals(productEntity.getPriceType(), result.getPriceType());
        assertEquals(productEntity.getStatus(), result.getStatus());
    }
}