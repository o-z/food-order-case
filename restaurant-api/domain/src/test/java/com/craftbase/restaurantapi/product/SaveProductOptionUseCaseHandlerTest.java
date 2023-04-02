package com.craftbase.restaurantapi.product;

import com.craftbase.restaurantapi.adapters.BaseProductOptionFakeAdapter;
import com.craftbase.restaurantapi.adapters.ProductFakeAdapter;
import com.craftbase.restaurantapi.adapters.ProductOptionFakeAdapter;
import com.craftbase.restaurantapi.adapters.RestaurantFranchisingFakeAdapter;
import com.craftbase.restaurantapi.common.model.enums.PriceType;
import com.craftbase.restaurantapi.product.model.dto.ProductOptionDto;
import com.craftbase.restaurantapi.product.model.enums.ProductStatus;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;
import com.craftbase.restaurantapi.product.usecase.SaveProduct;
import com.craftbase.restaurantapi.product.usecase.SaveProductOption;
import com.craftbase.restaurantapi.restaurant.usecase.SaveRestaurantFranchising;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaveProductOptionUseCaseHandlerTest {
    private final ProductOptionFakeAdapter productOptionFakeAdapter = new ProductOptionFakeAdapter();
    private final BaseProductOptionFakeAdapter baseProductOptionFakeAdapter = new BaseProductOptionFakeAdapter();
    private final ProductFakeAdapter productFakeAdapter = new ProductFakeAdapter();
    private final RestaurantFranchisingFakeAdapter restaurantFranchisingFakeAdapter = new RestaurantFranchisingFakeAdapter();
    private SaveProductOptionUseCaseHandler saveProductOptionUseCaseHandler =
            new SaveProductOptionUseCaseHandler(productOptionFakeAdapter,
                    baseProductOptionFakeAdapter,
                    productFakeAdapter,
                    restaurantFranchisingFakeAdapter);

    @Test
    public void testSaveProductOption_WhenIsNotError_ReturnUUID() {
        UUID restaurantFranchisingId = restaurantFranchisingFakeAdapter.saveRestaurantFranchising(SaveRestaurantFranchising.builder()
                .name("Restaurant1")
                .address("Address1")
                .country("Turkey")
                .build());

        UUID productId = productFakeAdapter.saveProduct(SaveProduct.builder()
                .name("Product1")
                .desc("Description1")
                .imageUrl("ImageUrl1")
                .price(BigDecimal.TEN)
                .priceType(PriceType.TRY)
                .restaurantFranchisingId(restaurantFranchisingId)
                .categoryId(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .build());

        UUID baseProductOptionId = baseProductOptionFakeAdapter.saveBaseProductOption(SaveBaseProductOption.builder()
                .name("BaseProductOption1")
                .desc("Description1")
                .defaultPrice(BigDecimal.ONE)
                .defaultPriceType(PriceType.TRY)
                .status(ProductStatus.ACTIVE)
                .build());

        SaveProductOption saveProductOption = SaveProductOption.builder()
                .name("ProductOption1")
                .desc("Description1")
                .price(BigDecimal.TEN)
                .priceType(PriceType.TRY)
                .productId(productId)
                .restaurantFranchisingId(restaurantFranchisingId)
                .baseProductOptionId(baseProductOptionId)
                .status(ProductStatus.ACTIVE)
                .build();


        UUID productOptionId = saveProductOptionUseCaseHandler.handle(saveProductOption);

        assertNotNull(productOptionId);
        ProductOptionDto productOptionDto = productOptionFakeAdapter.getProductOptionById(productOptionId);
        assertNotNull(productOptionDto);
        assertEquals(saveProductOption.getName(), productOptionDto.getName());
        assertEquals(saveProductOption.getDesc(), productOptionDto.getDesc());
        assertEquals(saveProductOption.getPrice(), productOptionDto.getPrice());
        assertEquals(saveProductOption.getPriceType(), productOptionDto.getPriceType());
        assertEquals(saveProductOption.getRestaurantFranchisingId(), productOptionDto.getRestaurantFranchisingId());
        assertEquals(saveProductOption.getStatus(), productOptionDto.getStatus());
    }
}
