package com.hedza06.cleanarch.customer;

import com.hedza06.cleanarch.customer.application.ports.out.CustomerPublisher;
import com.hedza06.cleanarch.customer.application.ports.out.CustomerRepository;
import com.hedza06.cleanarch.customer.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerPublisher<Customer> customerPublisher;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;


    @Test
    void shouldCreateNewCustomer()
    {
        Customer persistedCustomer = new Customer();
        persistedCustomer.setId(1);
        persistedCustomer.setFullName("Customer Full Name");
        persistedCustomer.setAddress("Customer Address 1");

        when(customerRepository.save(any(Customer.class))).thenReturn(persistedCustomer);

        customerService.create(persistedCustomer);

        verify(customerPublisher, times(1)).created(persistedCustomer);
    }

    @Test
    void shouldNotUpdateCustomerAndThrowIllegalArgumentExceptionWithIdNotProvided()
    {
        Customer customer = new Customer();
        customer.setFullName("Customer Full Name 2");
        customer.setAddress("Address 2");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> customerService.update(customer)
        );

        assertThat(exception.getMessage())
            .isEqualTo("Customer id is not provided! It's required for update operation.");
    }

    @Test
    void shouldUpdateCustomer()
    {
        Customer customer = new Customer();
        customer.setId(3);
        customer.setFullName("Customer Full Name 3");
        customer.setAddress("Address 3");

        when(customerRepository.update(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.update(customer);
        assertThat(updatedCustomer.getId()).isEqualTo(customer.getId());
        assertThat(updatedCustomer.getFullName()).isEqualTo(customer.getFullName());
        assertThat(updatedCustomer.getAddress()).isEqualTo(customer.getAddress());

        verify(customerRepository, times(1)).update(customer);
    }

    @Test
    void shouldReturnCustomerByGivenIdentifier()
    {
        Customer customer = new Customer();
        customer.setId(4);
        customer.setFullName("Customer Full Name 4");
        customer.setAddress("Address 4");

        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));

        customerService.findById(4);

        verify(customerRepository, times(1)).findById(4);
    }

    @Test
    void shouldDeleteCustomerWithGivenIdentifier()
    {
        Integer customerId = 5;
        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteById(customerId);

        verify(customerPublisher, times(1)).deleted(customerId);
    }

    @Test
    void shouldThrowAnExceptionForGivenNullIdentifier()
    {
        String exceptionMessage = "The given id must not be null";
        doThrow(new IllegalArgumentException(exceptionMessage)).when(customerRepository).deleteById(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            customerService.deleteById(null)
        );
        assertThat(exception.getMessage()).isEqualTo(exceptionMessage);

        verifyNoInteractions(customerPublisher);
    }
}