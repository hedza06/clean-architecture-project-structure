package com.hedza06.cleanarch.event_listeners;

import com.hedza06.cleanarch.CleanarchApplication;
import com.hedza06.cleanarch.customer.adapter.out.messaging.CustomerMessageEvent;
import com.hedza06.cleanarch.customer.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.hedza06.cleanarch.customer.enumerations.OperationEnum.CUSTOMER_CREATED;
import static com.hedza06.cleanarch.customer.enumerations.OperationEnum.CUSTOMER_DELETED;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CleanarchApplication.class)
class CustomerConsumerTest {

    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private CustomerConsumer customerConsumer;


    @Test
    void shouldReactOnCustomerCreateEventEvent()
    {
        Customer customer = new Customer();
        customer.setId(4);
        customer.setFullName("Customer Full Name 4");
        customer.setAddress("Customer Address 4");

        CustomerMessageEvent<Customer> customerMessageEvent = new CustomerMessageEvent<>(customer, CUSTOMER_CREATED);
        publisher.publishEvent(customerMessageEvent);

        verify(customerConsumer, times(1)).customerUpdated(customerMessageEvent);
    }

    @Test
    void shouldReactOnCustomerDeleteEventEvent()
    {
        Customer customer = new Customer();
        customer.setId(5);
        customer.setFullName("Customer Full Name 5");
        customer.setAddress("Customer Address 5");

        CustomerMessageEvent<Customer> customerMessageEvent = new CustomerMessageEvent<>(customer, CUSTOMER_DELETED);
        publisher.publishEvent(customerMessageEvent);

        verify(customerConsumer, times(1)).customerUpdated(customerMessageEvent);
    }
}