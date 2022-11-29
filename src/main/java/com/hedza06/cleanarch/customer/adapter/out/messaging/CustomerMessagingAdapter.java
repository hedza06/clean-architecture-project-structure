package com.hedza06.cleanarch.customer.adapter.out.messaging;

import com.hedza06.cleanarch.customer.application.ports.out.CustomerPublisher;
import com.hedza06.cleanarch.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.hedza06.cleanarch.customer.enumerations.OperationEnum.CUSTOMER_CREATED;
import static com.hedza06.cleanarch.customer.enumerations.OperationEnum.CUSTOMER_DELETED;


@Component
@RequiredArgsConstructor
public class CustomerMessagingAdapter implements CustomerPublisher<Customer> {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void created(Customer customer)
    {
        CustomerMessageEvent<Customer> customerMessageEvent = new CustomerMessageEvent<>(customer, CUSTOMER_CREATED);
        eventPublisher.publishEvent(customerMessageEvent);
    }

    @Override
    public void deleted(Integer id)
    {
        CustomerMessageEvent<Customer> customerMessageEvent = new CustomerMessageEvent<>(new Customer(id), CUSTOMER_DELETED);
        eventPublisher.publishEvent(customerMessageEvent);
    }
}
