package com.hedza06.cleanarch.customer.adapter.out.messaging;

import com.hedza06.cleanarch.customer.enumerations.OperationEnum;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class CustomerMessageEvent<T> extends ApplicationEvent {

    private final transient T data;
    private final OperationEnum operation;

    public CustomerMessageEvent(T source, OperationEnum operation)
    {
        super(source);
        this.data = source;
        this.operation = operation;
    }
}
