package com.hedza06.cleanarch.event_listeners;

import com.hedza06.cleanarch.customer.adapter.out.messaging.CustomerMessageEvent;
import com.hedza06.cleanarch.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerConsumer {

    @EventListener
    public void customerUpdated(CustomerMessageEvent<Customer> event)
    {
        log.info("Customer has been updated - event triggered! Data: {}", event);
        log.info("Operation type: {}", event.getOperation());
    }
}
