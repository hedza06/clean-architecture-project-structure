package com.hedza06.cleanarch.customer.application.ports.in;

import com.hedza06.cleanarch.customer.domain.Customer;

import java.util.Optional;

public interface CustomerUseCase
{
    Customer create(Customer customer);
    Customer update(Customer customer);
    Optional<Customer> findById(Integer id);
    void deleteById(Integer id);
}
