package com.hedza06.cleanarch.customer.application.ports.out;

import com.hedza06.cleanarch.customer.domain.Customer;

import java.util.Optional;

public interface CustomerRepository
{
    Customer save(Customer customer);
    Customer update(Customer customer);
    void deleteById(Integer id);

    Optional<Customer> findById(Integer id);
}
