package com.hedza06.cleanarch.customer.adapter.out.persistence;

import com.hedza06.cleanarch.customer.adapter.out.persistence.entities.CustomerEntity;
import com.hedza06.cleanarch.customer.adapter.out.persistence.repositories.CustomerJpaRepository;
import com.hedza06.cleanarch.customer.domain.Customer;
import com.hedza06.cleanarch.customer.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerPersistenceAdapterTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerPersistenceAdapter persistenceAdapter;


    @Test
    void shouldSaveCustomer()
    {
        Customer customerDomain = new Customer();
        customerDomain.setId(1);
        customerDomain.setFullName("Customer Full Name 1");
        customerDomain.setAddress("Customer Address 1");

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setFullName("Customer Full Name 1");
        customerEntity.setAddress("Customer Address 1");

        when(customerMapper.customerToCustomerEntity(customerDomain)).thenReturn(customerEntity);
        when(customerJpaRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.customerEntityToCustomer(customerEntity)).thenReturn(customerDomain);

        Customer customer = persistenceAdapter.save(customerDomain);

        assertThat(customer.getId()).isEqualTo(customerDomain.getId());
        assertThat(customer.getFullName()).isEqualTo(customerDomain.getFullName());
        assertThat(customer.getAddress()).isEqualTo(customerDomain.getAddress());
    }

    @Test
    void shouldReturnCustomerByGivenIdentifier()
    {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(2);
        customerEntity.setFullName("Customer Full Name 2");
        customerEntity.setAddress("Customer Address 2");

        Customer customerDomain = new Customer();
        customerDomain.setId(2);
        customerDomain.setFullName("Customer Full Name 2");
        customerDomain.setAddress("Customer Address 2");

        when(customerJpaRepository.findById(anyInt())).thenReturn(Optional.of(customerEntity));
        when(customerMapper.customerEntityToCustomer(customerEntity)).thenReturn(customerDomain);

        Optional<Customer> customerOptional = persistenceAdapter.findById(anyInt());
        assertThat(customerOptional).isPresent();
        assertThat(customerOptional.get().getId()).isEqualTo(customerDomain.getId());
        assertThat(customerOptional.get().getFullName()).isEqualTo(customerDomain.getFullName());
        assertThat(customerOptional.get().getAddress()).isEqualTo(customerDomain.getAddress());
    }

    @Test
    void shouldDeleteCustomerByGivenIdentifier()
    {
        Integer customerId = 3;

        doNothing().when(customerJpaRepository).deleteById(customerId);

        persistenceAdapter.deleteById(customerId);

        verify(customerJpaRepository, times(1)).deleteById(customerId);
    }
}