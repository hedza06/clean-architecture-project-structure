package com.hedza06.cleanarch.customer.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerDto {

    @NotNull(message = "Full name is null")
    @NotBlank(message = "Full name is blank")
    @NotEmpty(message = "Full name is empty")
    private String fullName;

    @NotNull(message = "Address is null")
    @NotBlank(message = "Address is blank")
    @NotEmpty(message = "Address is empty")
    private String address;

}
