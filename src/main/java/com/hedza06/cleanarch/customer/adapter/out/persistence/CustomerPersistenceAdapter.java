package com.hedza06.cleanarch.customer.adapter.out.persistence;

import com.hedza06.cleanarch.customer.adapter.out.persistence.entities.CustomerEntity;
import com.hedza06.cleanarch.customer.adapter.out.persistence.repositories.CustomerJpaRepository;
import com.hedza06.cleanarch.customer.application.ports.out.CustomerRepository;
import com.hedza06.cleanarch.customer.domain.Customer;
import com.hedza06.cleanarch.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;


    @Override
    public Customer save(Customer customer)
    {
        CustomerEntity customerEntity = customerJpaRepository.save(
            customerMapper.customerToCustomerEntity(customer)
        );
        return customerMapper.customerEntityToCustomer(customerEntity);
    }

    @Override
    public Customer update(Customer customer) {
        return save(customer);
    }

    @Override
    public Optional<Customer> findById(Integer id)
    {
        Optional<CustomerEntity> optionalCustomerEntity = customerJpaRepository.findById(id);
        return optionalCustomerEntity.map(customerMapper::customerEntityToCustomer);
    }

    @Override
    public void deleteById(Integer id) {
        customerJpaRepository.deleteById(id);
    }
}
