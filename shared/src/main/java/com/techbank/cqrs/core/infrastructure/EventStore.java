package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;

import java.util.Collection;
import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Collection<BaseEvent> event, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
}
