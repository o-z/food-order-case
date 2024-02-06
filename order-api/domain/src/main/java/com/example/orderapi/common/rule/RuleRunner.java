package com.example.orderapi.common.rule;

import com.example.orderapi.common.exception.OrderApiException;
import com.example.orderapi.common.model.dto.RuleResultDto;
import com.example.orderapi.common.util.DomainComponent;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
