package com.example.restaurantapi.product;

import com.example.restaurantapi.adapters.BaseProductFakeAdapter;
import com.example.restaurantapi.adapters.ProductFakeAdapter;
import com.example.restaurantapi.adapters.RestaurantFakeAdapter;
import com.example.restaurantapi.adapters.RestaurantFranchisingFakeAdapter;
import com.example.restaurantapi.common.model.enums.PriceType;
import com.example.restaurantapi.product.model.dto.ProductDto;
import com.example.restaurantapi.product.model.enums.ProductStatus;
import com.example.restaurantapi.product.usecase.SaveBaseProduct;
import com.example.restaurantapi.product.usecase.SaveProduct;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurant;
import com.example.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaveProductUseCaseHandlerTest {
    private final ProductFakeAdapter productFakeAdapter = new ProductFakeAdapter();
    private final BaseProductFakeAdapter baseProductFakeAdapter = new BaseProductFakeAdapter();
    private final RestaurantFakeAdapter restaurantFakeAdapter = new RestaurantFakeAdapter();
    private final RestaurantFranchisingFakeAdapter restaurantFranchisingFakeAdapter = new RestaurantFranchisingFakeAdapter();
    private SaveProductUseCaseHandler saveProductUseCaseHandler =
            new SaveProductUseCaseHandler(productFakeAdapter,
                    baseProductFakeAdapter,
                    restaurantFranchisingFakeAdapter);

    @Test
    public void testSaveProduct_WhenIsNotError_ReturnUUID() {

        UUID restaurantId = restaurantFakeAdapter.saveRestaurant(SaveRestaurant.builder().name("Restaurant").build());

        UUID restaurantFranchisingId = restaurantFranchisingFakeAdapter.saveRestaurantFranchising(SaveRestaurantFranchising.builder()
                .name("Restaurant Franchising")
                .address("Address1")
                .country("Turkey")
                .restaurantId(restaurantId)
                .build());

        UUID baseProductId = baseProductFakeAdapter.saveBaseProduct(SaveBaseProduct.builder()
                .name("BaseProduct1")
                .desc("Description1")
                .defaultPrice(BigDecimal.TEN)
                .defaultPriceType(PriceType.TRY)
                .restaurantId(restaurantFranchisingFakeAdapter.getRestaurantFranchisingById(restaurantFranchisingId).getRestaurantId())
                .categoryId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .build());
        SaveProduct saveProduct = SaveProduct.builder()
                .name("Product1")
                .desc("Description1")
                .imageUrl("ImageUrl1")
                .price(BigDecimal.valueOf(20))
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(restaurantFranchisingId)
                .baseProductId(baseProductId)
                .categoryId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .build();

        
        UUID productId = saveProductUseCaseHandler.handle(saveProduct);

        
        assertNotNull(productId);
        ProductDto productDto = productFakeAdapter.getProductById(productId);
        assertNotNull(productDto);
        assertEquals(saveProduct.getName(), productDto.getName());
        assertEquals(saveProduct.getDesc(), productDto.getDesc());
        assertEquals(saveProduct.getImageUrl(), productDto.getImageUrl());
        assertEquals(saveProduct.getPrice(), productDto.getPrice());
        assertEquals(saveProduct.getPriceType(), productDto.getPriceType());
        assertEquals(saveProduct.getRestaurantFranchisingId(), productDto.getRestaurantFranchisingId());
        assertEquals(saveProduct.getStatus(), productDto.getStatus());
    }
}
