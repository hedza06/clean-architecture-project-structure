package com.hedza06.cleanarch.customer.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerResponseDto {

    private Integer id;
    private String fullName;
    private String address;

}
