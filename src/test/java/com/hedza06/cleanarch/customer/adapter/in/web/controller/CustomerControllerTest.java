package com.hedza06.cleanarch.customer.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerDto;
import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerResponseDto;
import com.hedza06.cleanarch.customer.application.ports.in.CustomerUseCase;
import com.hedza06.cleanarch.customer.domain.Customer;
import com.hedza06.cleanarch.customer.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private static final String API = "/api/customers";

    @MockBean
    private CustomerUseCase customerUseCase;

    @MockBean
    private CustomerMapper customerMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnValidationErrorOnFullNameForGivenCustomer() throws Exception
    {
        CreateCustomerDto customer = new CreateCustomerDto();
        customer.setAddress("Address 1");

        mockMvc
            .perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[*].fieldName", containsInAnyOrder("fullName", "fullName", "fullName")))
            .andExpect(jsonPath("$.[*].errorMessage", containsInAnyOrder("Full name is null", "Full name is blank", "Full name is empty")));
    }

    @Test
    void shouldReturnValidationErrorOnAddressForGivenCustomer() throws Exception
    {
        CreateCustomerDto customer = new CreateCustomerDto();
        customer.setFullName("Customer Full Name 1");

        mockMvc
            .perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(customer))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[*].fieldName", containsInAnyOrder("address", "address", "address")))
            .andExpect(jsonPath("$.[*].errorMessage", containsInAnyOrder("Address is null", "Address is blank", "Address is empty")));
    }

    @Test
    void shouldCreateNewCustomer() throws Exception
    {
        CreateCustomerDto customer = new CreateCustomerDto();
        customer.setFullName("Customer Full Name 2");
        customer.setAddress("Address 2");

        Customer customerDomain = new Customer();
        customerDomain.setId(2);
        customerDomain.setFullName("Customer Full Name 2");
        customerDomain.setAddress("Address 2");

        CreateCustomerResponseDto customerResponseDto = new CreateCustomerResponseDto(
            2, "Customer Full Name 2", "Address 2"
        );

        when(customerMapper.createCustomerDtoToCustomer(customer)).thenReturn(customerDomain);
        when(customerUseCase.create(customerDomain)).thenReturn(customerDomain);
        when(customerMapper.customerToCreateCustomerResponseDto(customerDomain)).thenReturn(customerResponseDto);

        mockMvc
            .perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(customer))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(customerResponseDto.getId()))
            .andExpect(jsonPath("$.fullName").value(customerResponseDto.getFullName()))
            .andExpect(jsonPath("$.address").value(customerResponseDto.getAddress()));
    }
}