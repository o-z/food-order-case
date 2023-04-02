package com.craftbase.orderapi.common.model.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements Serializable {

    private UUID id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
