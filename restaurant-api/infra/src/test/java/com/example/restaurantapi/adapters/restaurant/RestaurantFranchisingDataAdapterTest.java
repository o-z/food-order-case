package com.example.restaurantapi.adapters.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.restaurantapi.adapters.restaurant.jpa.RestaurantFranchisingDataAdapter;
import com.example.restaurantapi.adapters.restaurant.jpa.entity.RestaurantEntity;
import com.example.restaurantapi.adapters.restaurant.jpa.entity.RestaurantFranchisingEntity;
import com.example.restaurantapi.adapters.restaurant.jpa.repository.RestaurantFranchisingRepository;
import com.example.restaurantapi.adapters.restaurant.jpa.repository.RestaurantRepository;
import com.example.restaurantapi.common.exception.RestaurantApiException;
import com.example.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantFranchisingDataAdapterTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantFranchisingRepository restaurantFranchisingRepository;

    @InjectMocks
    private RestaurantFranchisingDataAdapter restaurantFranchisingDataAdapter;

    @Test
    public void saveRestaurantFranchising_validRequest_shouldReturnUUID() {
        UUID expectedId = UUID.randomUUID();
        SaveRestaurantFranchising saveRestaurantFranchising = SaveRestaurantFranchising.builder()
                .name("Franchise Name")
                .desc("Franchise Desc")
                .country("Franchise Country")
                .address("Franchise Address")
                .restaurantId(UUID.randomUUID())
                .status(RestaurantStatus.ACTIVE)
                .build();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(saveRestaurantFranchising.getRestaurantId())
                .restaurantFranchisingEntities(Collections.singletonList(
                        RestaurantFranchisingEntity.builder()
                                .name("Franchise Name 1")
                                .build()
                ))
                .build();

        Mockito.when(restaurantRepository.findById(saveRestaurantFranchising.getRestaurantId())).thenReturn(Optional.of(restaurantEntity));
        Mockito.when(restaurantFranchisingRepository.save(Mockito.any(RestaurantFranchisingEntity.class)))
                .thenReturn(RestaurantFranchisingEntity.builder().id(UUID.randomUUID()).build());
        UUID result = restaurantFranchisingDataAdapter.saveRestaurantFranchising(saveRestaurantFranchising);

        assertNotNull(result);
        assertEquals(expectedId.getClass(), result.getClass());
    }

    @Test(expected = RestaurantApiException.class)
    public void saveRestaurantFranchising_nonExistingRestaurant_shouldThrowException() {
        SaveRestaurantFranchising saveRestaurantFranchising = SaveRestaurantFranchising.builder()
                .name("Franchise Name")
                .desc("Franchise Desc")
                .country("Franchise Country")
                .address("Franchise Address")
                .restaurantId(UUID.randomUUID())
                .status(RestaurantStatus.ACTIVE)
                .build();

        Mockito.when(restaurantRepository.findById(saveRestaurantFranchising.getRestaurantId())).thenReturn(Optional.empty());
        restaurantFranchisingDataAdapter.saveRestaurantFranchising(saveRestaurantFranchising);
    }

    @Test(expected = RestaurantApiException.class)
    public void saveRestaurantFranchising_alreadyExistingFranchise_shouldThrowException() {
        UUID restaurantId = UUID.randomUUID();
        String franchiseName = "Franchise Name";
        SaveRestaurantFranchising saveRestaurantFranchising = SaveRestaurantFranchising.builder()
                .name(franchiseName)
                .desc("Franchise Desc")
                .country("Franchise Country")
                .address("Franchise Address")
                .restaurantId(restaurantId)
                .status(RestaurantStatus.ACTIVE)
                .build();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(restaurantId)
                .restaurantFranchisingEntities(Collections.singletonList(
                        RestaurantFranchisingEntity.builder()
                                .name(franchiseName)
                                .build()
                ))
                .build();

        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
        restaurantFranchisingDataAdapter.saveRestaurantFranchising(saveRestaurantFranchising);
    }


    @Test
    public void isRestaurantFranchisingExistById_existingFranchise_shouldReturnTrue() {
        UUID franchiseId = UUID.randomUUID();
        Mockito.when(restaurantFranchisingRepository.existsById(franchiseId))
                .thenReturn(true);

        boolean result = restaurantFranchisingDataAdapter.isRestaurantFranchisingExistById(franchiseId);

        assertTrue(result);
    }

    @Test
    public void isRestaurantFranchisingExistById_nonExistingFranchise_shouldReturnFalse() {
        UUID franchiseId = UUID.randomUUID();
        Mockito.when(restaurantFranchisingRepository.existsById(franchiseId))
                .thenReturn(false);

        boolean result = restaurantFranchisingDataAdapter.isRestaurantFranchisingExistById(franchiseId);

        assertFalse(result);
    }
}