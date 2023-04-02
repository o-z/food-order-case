package com.craftbase.orderapi.adapters.product;

import com.craftbase.orderapi.adapters.product.client.ProductApiClient;
import com.craftbase.orderapi.adapters.product.model.dto.ProductAdapterDto;
import com.craftbase.orderapi.adapters.product.rest.ProductRestAdapter;
import com.craftbase.orderapi.product.model.dto.ProductDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductRestAdapterTest {

    @Mock
    private ProductApiClient productApiClient;

    @InjectMocks
    private ProductRestAdapter productRestAdapter;

    @Test
    public void testGetProductById_WhenProductExist_ReturnProductDto() {
        
        UUID productId = UUID.randomUUID();
        ProductAdapterDto productAdapterDto = ProductAdapterDto.builder()
                .id(productId)
                .name("Test Product")
                .price(new BigDecimal(10))
                .build();
        given(productApiClient.getProductById(productId)).willReturn(productAdapterDto);

        
        ProductDto result = productRestAdapter.getProductById(productId);

        
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(new BigDecimal(10), result.getPrice());
    }

    @Test
    public void testGetProductByIdSet_WhenProductsExist_ReturnProductMap() {
        
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        ProductAdapterDto productAdapterDto1 = ProductAdapterDto.builder()
                .id(productId1)
                .name("Test Product 1")
                .price(new BigDecimal(10))
                .build();
        ProductAdapterDto productAdapterDto2 = ProductAdapterDto.builder()
                .id(productId2)
                .name("Test Product 2")
                .price(new BigDecimal(20))
                .build();
        Map<UUID, ProductAdapterDto> productAdapterDtoMap = Map.of(productId1, productAdapterDto1, productId2, productAdapterDto2);
        given(productApiClient.getProductByIdSet(Set.of(productId1, productId2))).willReturn(productAdapterDtoMap);

        
        Map<UUID, ProductDto> result = productRestAdapter.getProductByIdSet(Set.of(productId1, productId2));

        
        assertEquals(2, result.size());
        assertTrue(result.containsKey(productId1));
        assertTrue(result.containsKey(productId2));
        assertEquals("Test Product 1", result.get(productId1).getName());
        assertEquals(new BigDecimal(10), result.get(productId1).getPrice());
        assertEquals("Test Product 2", result.get(productId2).getName());
        assertEquals(new BigDecimal(20), result.get(productId2).getPrice());
    }
}