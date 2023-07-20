package com.techbank.account.base.service;

import com.techbank.account.base.aggregate.AggregateRoot;

public interface EventSourcingService<T> {
    void save(AggregateRoot aggregateRoot);

    //returns latest state of the aggregate
    T getById(String aggregateId);
}
