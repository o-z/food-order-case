package com.craftbase.orderapi.common.rule;

import com.craftbase.orderapi.common.exception.OrderApiException;
import com.craftbase.orderapi.common.model.dto.RuleResultDto;
import com.craftbase.orderapi.common.util.DomainComponent;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@DomainComponent
@RequiredArgsConstructor
public class RuleRunner<T> {
    private final List<IRule<T>> iRules;

    public void validate(T t) {
        iRules.stream()
                .filter(iRule -> iRule.isValidFor(t))
                .sorted(Comparator.comparingInt(IRule::getRuleSortNumber))
                .forEach(iRule -> {
                    RuleResultDto ruleResultDto = iRule.validate(t);
                    if (!ruleResultDto.isSuccess()) {
                        throw new OrderApiException(ruleResultDto.getErrorCode());
                    }
                });
    }
}
