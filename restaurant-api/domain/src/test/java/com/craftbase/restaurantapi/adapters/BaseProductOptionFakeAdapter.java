package com.craftbase.restaurantapi.adapters;

import com.craftbase.restaurantapi.product.model.dto.BaseProductOptionDto;
import com.craftbase.restaurantapi.product.port.BaseProductOptionPort;
import com.craftbase.restaurantapi.product.usecase.SaveBaseProductOption;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseProductOptionFakeAdapter implements BaseProductOptionPort {

    private Map<UUID, BaseProductOptionDto> productOptionDtoMap = new HashMap<>();

    @Override
    public UUID saveBaseProductOption(SaveBaseProductOption saveBaseProductOption) {
        BaseProductOptionDto dto = BaseProductOptionDto.builder()
                .id(UUID.randomUUID())
                .name(saveBaseProductOption.getName())
                .desc(saveBaseProductOption.getDesc())
                .defaultPrice(saveBaseProductOption.getDefaultPrice())
                .defaultPriceType(saveBaseProductOption.getDefaultPriceType())
                .status(saveBaseProductOption.getStatus())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        productOptionDtoMap.put(dto.getId(), dto);
        return dto.getId();
    }

    @Override
    public BaseProductOptionDto getBaseProductOptionById(UUID baseProductOptionId) {
        return productOptionDtoMap.get(baseProductOptionId);
    }
}
