package com.hedza06.cleanarch.customer.application.ports.out;

public interface CustomerPublisher<T>
{
    void created(T message);
    void deleted(Integer id);
}
