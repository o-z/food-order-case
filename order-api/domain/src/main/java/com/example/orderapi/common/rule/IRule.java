package com.example.orderapi.common.rule;

import com.example.orderapi.common.model.dto.RuleResultDto;

public interface IRule<T> {
    RuleResultDto validate(T clazz);

    Integer getRuleSortNumber();

    boolean isValidFor(Object obj);
}
