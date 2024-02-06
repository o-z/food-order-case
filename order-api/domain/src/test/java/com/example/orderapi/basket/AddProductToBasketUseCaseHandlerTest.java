package com.example.orderapi.basket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.orderapi.adapters.BasketFakeAdapter;
import com.example.orderapi.adapters.BasketLockFakeAdapter;
import com.example.orderapi.adapters.ProductFakeAdapter;
import com.example.orderapi.basket.model.dto.BasketDto;
import com.example.orderapi.basket.model.dto.BasketProductDto;
import com.example.orderapi.basket.model.dto.BasketProductOptionDto;
import com.example.orderapi.basket.rule.BasketProductActivityRule;
import com.example.orderapi.basket.usecase.AddProductToBasket;
import com.example.orderapi.common.model.enums.PriceType;
import com.example.orderapi.common.rule.RuleRunner;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

public class AddProductToBasketUseCaseHandlerTest {
    BasketFakeAdapter basketFakeAdapter = new BasketFakeAdapter();

    private final AddProductToBasketUseCaseHandler handler = new AddProductToBasketUseCaseHandler(new ProductFakeAdapter(),
            basketFakeAdapter,
            new BasketLockFakeAdapter(),
            new RuleRunner<>(List.of(new BasketProductActivityRule())));

    @Test
    public void testAddProductToBasket_WhenProductIsNotPassive_ReturnNothing() {
        AddProductToBasket addProductToBasket = AddProductToBasket.builder()
                .basketId(UUID.fromString("5d7ab8b2-59fc-4c84-a83b-f38804ad158b"))
                .productId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                .productOptionIds(List.of(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001")))
                .build();


        handler.handle(addProductToBasket);
        BasketDto actualBasketDto = basketFakeAdapter.getBasketByUserId(UUID.fromString("5d7ab8b2-59fc-4c84-a83b-f38804ad158b"));
        BasketDto expectedBasketDto = getExpectedBasketDto();

        assertEquals(expectedBasketDto.getCalculatedPrice(), actualBasketDto.getCalculatedPrice());
        assertEquals(expectedBasketDto.getPriceType(), actualBasketDto.getPriceType());
        assertEquals(expectedBasketDto.getComment(), actualBasketDto.getComment());
        assertEquals(expectedBasketDto.getUserId(), actualBasketDto.getUserId());
        assertEquals(expectedBasketDto.getUserAddressId(), actualBasketDto.getUserAddressId());
        assertEquals(expectedBasketDto.getBasketProducts().size(), actualBasketDto.getBasketProducts().size());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getProductId(), actualBasketDto.getBasketProducts().get(0).getProductId());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getName(), actualBasketDto.getBasketProducts().get(0).getName());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getCalculatedPrice(), actualBasketDto.getBasketProducts().get(0).getCalculatedPrice());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getBasketProductOptions().size(), actualBasketDto.getBasketProducts().get(0).getBasketProductOptions().size());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getProductOptionId(), actualBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getProductOptionId());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getName(), actualBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getName());
        assertEquals(expectedBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getCalculatedPrice(), actualBasketDto.getBasketProducts().get(0).getBasketProductOptions().get(0).getCalculatedPrice());


    }

    private BasketDto getExpectedBasketDto() {
        return BasketDto.builder()
                .calculatedPrice(BigDecimal.valueOf(15.0))
                .priceType(PriceType.TRY)
                .comment("This is a comment")
                .userId(UUID.fromString("5d7ab8b2-59fc-4c84-a83b-f38804ad158b"))
                .userAddressId(UUID.fromString("fedc48db-8411-492f-aa53-1f33a51e281c"))
                .basketProducts(Collections.singletonList(
                        BasketProductDto.builder()
                                .productId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120000"))
                                .name("Cheeseburger")
                                .calculatedPrice(BigDecimal.valueOf(10.99))
                                .basketProductOptions(Collections.singletonList(
                                        BasketProductOptionDto.builder()
                                                .productOptionId(UUID.fromString("6df52be8-ceb1-11ed-afa1-0242ac120001"))
                                                .name("Extra Cheese")
                                                .calculatedPrice(BigDecimal.valueOf(5.0))
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
}
