package com.example.orderapi.common.model.dto;

import com.example.orderapi.common.exception.ErrorCode;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleResultDto implements Serializable {
    private boolean isSuccess;
    private ErrorCode errorCode;
}
