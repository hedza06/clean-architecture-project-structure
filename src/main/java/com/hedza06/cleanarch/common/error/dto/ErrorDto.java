package com.hedza06.cleanarch.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String fieldName;
    private String errorMessage;

}
