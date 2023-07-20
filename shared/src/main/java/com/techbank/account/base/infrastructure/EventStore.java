package com.techbank.account.base.infrastructure;

import com.techbank.account.base.events.BaseEventDto;

import java.util.Collection;
import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Collection<BaseEventDto> event, int expectedVersion);
    List<BaseEventDto> getEvents(String aggregateId);
}
