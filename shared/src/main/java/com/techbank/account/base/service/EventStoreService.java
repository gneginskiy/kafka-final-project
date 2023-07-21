package com.techbank.account.base.service;

import com.techbank.account.base.events.BaseEvent;

import java.util.Collection;
import java.util.List;

public interface EventStoreService {
    void saveEvents(String aggregateId, Collection<BaseEvent> event, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
}
