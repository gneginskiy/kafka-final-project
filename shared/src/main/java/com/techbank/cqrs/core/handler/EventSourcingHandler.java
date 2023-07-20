package com.techbank.cqrs.core.handler;

import com.techbank.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);

    //returns latest state of the aggregate
    T getById(String aggregateId);
}
