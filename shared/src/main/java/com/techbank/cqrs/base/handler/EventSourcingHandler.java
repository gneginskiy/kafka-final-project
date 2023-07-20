package com.techbank.cqrs.base.handler;

import com.techbank.cqrs.base.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);

    //returns latest state of the aggregate
    T getById(String aggregateId);
}
