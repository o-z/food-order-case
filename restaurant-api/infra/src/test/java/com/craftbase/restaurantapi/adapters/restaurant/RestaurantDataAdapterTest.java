package com.craftbase.restaurantapi.adapters.restaurant;

import com.craftbase.restaurantapi.adapters.restaurant.jpa.RestaurantDataAdapter;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.craftbase.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.craftbase.restaurantapi.restaurant.model.dto.RestaurantDto;
import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantDataAdapterTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantDataAdapter restaurantDataAdapter;

    @Test
    public void testGetRestaurantById_WhenGetRestaurantByIdIsCalled_ReturnRestaurantDto() {
        UUID id = UUID.randomUUID();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(id)
                .name("Test Restaurant")
                .desc("Test Description")
                .imageUrl("http://test-image-url.com")
                .status(RestaurantStatus.ACTIVE)
                .build();
        Mockito.when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurantEntity));

        RestaurantDto expected = RestaurantDto.builder()
                .id(id)
                .name("Test Restaurant")
                .desc("Test Description")
                .imageUrl("http://test-image-url.com")
                .status(RestaurantStatus.ACTIVE)
                .build();

        RestaurantDto actual = restaurantDataAdapter.getRestaurantById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsRestaurantExistById_WhenIsRestaurantNotExistByIdIsCalledWithNonExistingId_ReturnTrue() {
        UUID id = UUID.randomUUID();
        Mockito.when(restaurantRepository.existsById(id)).thenReturn(true);

        boolean actual = restaurantDataAdapter.isRestaurantExistById(id);

        Assert.assertTrue(actual);
    }
    @Test
    public void testIsRestaurantExistById_WhenIsRestaurantExistByIdIsCalledWithNonExistingId_ReturnFalse() {
        UUID id = UUID.randomUUID();
        Mockito.when(restaurantRepository.existsById(id)).thenReturn(false);

        boolean actual = restaurantDataAdapter.isRestaurantExistById(id);

        Assert.assertFalse(actual);
    }

    @Test
    public void testSaveRestaurant_WhenIsNotError_ReturnRestaurantId() {
        SaveRestaurant saveRestaurant = SaveRestaurant.builder()
                .name("Test Restaurant")
                .desc("Test Description")
                .imageUrl("http://test-image-url.com")
                .status(RestaurantStatus.ACTIVE)
                .build();
        UUID id = UUID.fromString("e1cf9b88-5697-4ad1-a2f9-090c16f4b992");
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(id)
                .name("Test Restaurant")
                .desc("Test Description")
                .imageUrl("http://test-image-url.com")
                .status(RestaurantStatus.ACTIVE)
                .build();
        Mockito.when(restaurantRepository.save(any())).thenReturn(restaurantEntity);

        UUID actual = restaurantDataAdapter.saveRestaurant(saveRestaurant);

        Assert.assertEquals(id, actual);
    }
}