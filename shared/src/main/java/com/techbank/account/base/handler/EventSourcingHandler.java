package com.techbank.account.base.handler;

import com.techbank.account.base.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);

    //returns latest state of the aggregate
    T getById(String aggregateId);
}
