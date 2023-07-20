package com.techbank.account.base.service;

import com.techbank.account.base.events.BaseEventDto;

import java.util.Collection;
import java.util.List;

public interface EventStoreService {
    void saveEvents(String aggregateId, Collection<BaseEventDto> event, int expectedVersion);
    List<BaseEventDto> getEvents(String aggregateId);
}
