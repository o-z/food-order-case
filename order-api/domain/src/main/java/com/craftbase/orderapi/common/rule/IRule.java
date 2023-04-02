package com.craftbase.orderapi.common.rule;

import com.craftbase.orderapi.common.model.dto.RuleResultDto;

public interface IRule<T> {
    RuleResultDto validate(T clazz);

    Integer getRuleSortNumber();

    boolean isValidFor(Object obj);
}
