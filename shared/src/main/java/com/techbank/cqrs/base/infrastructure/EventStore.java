package com.techbank.cqrs.base.infrastructure;

import com.techbank.cqrs.base.events.BaseEventDto;

import java.util.Collection;
import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Collection<BaseEventDto> event, int expectedVersion);
    List<BaseEventDto> getEvents(String aggregateId);
}
