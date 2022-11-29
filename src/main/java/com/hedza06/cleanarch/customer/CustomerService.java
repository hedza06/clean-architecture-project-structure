package com.hedza06.cleanarch.customer;

import com.hedza06.cleanarch.customer.application.ports.in.CustomerUseCase;
import com.hedza06.cleanarch.customer.application.ports.out.CustomerPublisher;
import com.hedza06.cleanarch.customer.application.ports.out.CustomerRepository;
import com.hedza06.cleanarch.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements CustomerUseCase {

    private final CustomerPublisher<Customer> customerPublisher;
    private final CustomerRepository customerRepository;


    @Override
    public Customer create(Customer customer)
    {
        Customer createdCustomer = customerRepository.save(customer);
        customerPublisher.created(createdCustomer);

        return createdCustomer;
    }

    @Override
    public Customer update(Customer customer)
    {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Customer id is not provided! It's required for update operation.");
        }
        return customerRepository.update(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id)
    {
        customerRepository.deleteById(id);
        customerPublisher.deleted(id);
    }
}
