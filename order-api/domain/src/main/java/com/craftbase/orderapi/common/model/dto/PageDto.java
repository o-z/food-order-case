package com.craftbase.orderapi.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T extends Serializable> implements Serializable {
    private List<T> contents;
    private Integer page;
    private Integer size;
    private Integer totalPage;
}
