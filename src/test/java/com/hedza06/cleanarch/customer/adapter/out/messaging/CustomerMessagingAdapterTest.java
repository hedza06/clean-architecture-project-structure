package com.hedza06.cleanarch.customer.adapter.out.messaging;

import com.hedza06.cleanarch.customer.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerMessagingAdapterTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CustomerMessagingAdapter messagingAdapter;


    @Test
    void shouldPublishCreateEvent()
    {
        Customer customerDomain = new Customer();
        customerDomain.setId(1);
        customerDomain.setFullName("Customer Full Name 1");
        customerDomain.setAddress("Customer Address 1");

        messagingAdapter.created(customerDomain);

        verify(eventPublisher, times(1)).publishEvent(ArgumentMatchers.any());
    }

    @Test
    void shouldPublishDeletedEvent()
    {
        messagingAdapter.deleted(anyInt());
        verify(eventPublisher, times(1)).publishEvent(ArgumentMatchers.any());
    }
}