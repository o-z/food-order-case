package com.craftbase.orderapi.common.model.dto;

import com.craftbase.orderapi.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleResultDto implements Serializable {
    private boolean isSuccess;
    private ErrorCode errorCode;
}
