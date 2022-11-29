package com.hedza06.cleanarch.customer.mapper;

import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerDto;
import com.hedza06.cleanarch.customer.adapter.in.web.dto.CreateCustomerResponseDto;
import com.hedza06.cleanarch.customer.adapter.out.persistence.entities.CustomerEntity;
import com.hedza06.cleanarch.customer.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper
{
    CustomerEntity customerToCustomerEntity(Customer customer);
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
    Customer createCustomerDtoToCustomer(CreateCustomerDto createCustomerDto);

    CreateCustomerResponseDto customerToCreateCustomerResponseDto(Customer createdCustomer);
}
