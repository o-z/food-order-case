package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.adapters.BaseProductFakeAdapter;
import com.craftbase.restaurantapi.adapters.RestaurantFakeAdapter;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.BaseProductDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProduct;
import com.craftbase.restaurantapi.restaurant.model.enums.RestaurantStatus;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurant;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaveBaseProductUseCaseHandlerTest {
    private final BaseProductFakeAdapter baseProductFakeAdapter = new BaseProductFakeAdapter();
    private final RestaurantFakeAdapter restaurantFakeAdapter = new RestaurantFakeAdapter();
    private SaveBaseProductUseCaseHandler saveBaseProductUseCaseHandler =
            new SaveBaseProductUseCaseHandler(baseProductFakeAdapter,
                    restaurantFakeAdapter);

    @Test
    public void testSaveBaseProduct_WhenIsNotError_ReturnUUID() {
        UUID restaurantId = restaurantFakeAdapter.saveRestaurant(SaveRestaurant.builder()
                .name("Restaurant1")
                .desc("Restaurant1")
                .imageUrl("Restaurant1")
                .status(RestaurantStatus.ACTIVE)
                .build());


        SaveBaseProduct saveBaseProduct = SaveBaseProduct.builder()
                .name("BaseProduct1")
                .desc("Description1")
                .defaultPrice(BigDecimal.TEN)
                .defaultPriceType(PriceType.TRY)
                .restaurantId(restaurantId)
                .categoryId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .build();

        UUID baseProductId = saveBaseProductUseCaseHandler.handle(saveBaseProduct);
        BaseProductDto baseProductDto = baseProductFakeAdapter.getBaseProductById(baseProductId);

        assertNotNull(baseProductId);
        assertNotNull(baseProductDto);
        assertEquals(saveBaseProduct.getName(), baseProductDto.getName());
        assertEquals(saveBaseProduct.getDesc(), baseProductDto.getDesc());
        assertEquals(saveBaseProduct.getDefaultPrice(), baseProductDto.getDefaultPrice());
        assertEquals(saveBaseProduct.getDefaultPriceType(), baseProductDto.getDefaultPriceType());
        assertEquals(saveBaseProduct.getRestaurantId(), baseProductDto.getRestaurantId());
        assertEquals(saveBaseProduct.getStatus(), baseProductDto.getStatus());
    }
}
