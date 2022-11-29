package com.hedza06.cleanarch.customer.adapter.in.web.controller;

import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerDto;
import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerResponseDto;
import com.hedza06.cleanarch.customer.application.ports.in.CustomerUseCase;
import com.hedza06.cleanarch.customer.domain.Customer;
import com.hedza06.cleanarch.customer.mapper.CustomerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerUseCase customerUseCase;
    private final CustomerMapper customerMapper;


    @PostMapping
    public ResponseEntity<CreateCustomerResponseDto> create(@RequestBody @Valid CreateCustomerDto createCustomerDto)
    {
        Customer customer = customerMapper.createCustomerDtoToCustomer(createCustomerDto);
        Customer createdCustomer = customerUseCase.create(customer);

        CreateCustomerResponseDto customerResponseDto = customerMapper.customerToCreateCustomerResponseDto(createdCustomer);
        return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
    }

}
