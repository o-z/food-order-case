package com.example.orderapi.adapters.basket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.orderapi.adapters.basket.jpa.BasketDataAdapter;
import com.example.orderapi.adapters.basket.jpa.entity.BasketEntity;
import com.example.orderapi.adapters.basket.jpa.entity.BasketProductEntity;
import com.example.orderapi.adapters.basket.jpa.repository.BasketProductOptionRepository;
import com.example.orderapi.adapters.basket.jpa.repository.BasketProductRepository;
import com.example.orderapi.adapters.basket.jpa.repository.BasketRepository;
import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.exception.OrderApiException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasketDataAdapterTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketProductRepository basketProductRepository;

    @Mock
    private BasketProductOptionRepository basketProductOptionRepository;

    @InjectMocks
    private BasketDataAdapter basketDataAdapter;

    @Test
    public void testGetBasketByUserId_WhenBasketExists_ReturnBasket() {
        UUID userId = UUID.randomUUID();
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setUserId(userId);
        when(basketRepository.findByUserId(userId)).thenReturn(Optional.of(basketEntity));

        BasketDto basketDto = basketDataAdapter.getBasketByUserId(userId);

        assertNotNull(basketDto);
        assertEquals(userId, basketDto.getUserId());
    }

    @Test
    public void testGetBasket_WhenBasketNotFound_ThrowException() {
        UUID userId = UUID.randomUUID();
        when(basketRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(OrderApiException.class, () -> basketDataAdapter.getBasketByUserId(userId));
    }

    @Test
    public void testRemoveProductsFromBasket_WhenBasketExists_RemoveAllProducts() {
        UUID basketId = UUID.randomUUID();
        List<BasketProductEntity> basketProductEntities = new LinkedList<>(List.of(BasketProductEntity.builder().build()));

        BasketEntity basketEntity = BasketEntity.builder()
                .id(basketId)
                .basketProductEntities(basketProductEntities)
                .build();
        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basketEntity));

        basketDataAdapter.clearBasket(basketId);

        verify(basketRepository).save(eq(basketEntity));
    }

    @Test
    public void testRemoveProductsFromBasket_WhenBasketNotFound_ThrowException() {
        UUID basketId = UUID.randomUUID();
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());

        assertThrows(OrderApiException.class, () -> basketDataAdapter.clearBasket(basketId));
    }

    @Test
    public void testAddProductToBasket_WhenBasketExists_AddProductWithProductOptions() {
        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<UUID> productOptionIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        BasketEntity basketEntity = BasketEntity.builder()
                .id(basketId)
                .build();
        when(basketRepository.findById(basketId)).thenReturn(Optional.of(basketEntity));
        when(basketProductRepository.save(any(BasketProductEntity.class))).thenAnswer(invocation -> {
            BasketProductEntity basketProductEntity = invocation.getArgument(0);
            basketProductEntity.setId(UUID.randomUUID());
            return basketProductEntity;
        });

        basketDataAdapter.addProductToBasket(new AddProductToBasket(basketId, productId, productOptionIds));

        verify(basketProductRepository).save(any(BasketProductEntity.class));
        verify(basketProductOptionRepository).saveAll(anyList());
    }

    @Test
    public void testAddProductToBasket_WhenBasketNotFound_ThrowException() {

        UUID basketId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        List<UUID> productOptionIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        when(basketRepository.findById(basketId)).thenReturn(Optional.empty());

        assertThrows(OrderApiException.class, () -> basketDataAdapter.addProductToBasket(new AddProductToBasket(basketId, productId, productOptionIds)));

    }
}

