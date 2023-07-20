package com.techbank.account.cmd.infrastructure;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.account.cmd.domain.EventStoreRepository;
import com.techbank.account.cmd.infrastructure.exceptions.ConcurrencyException;
import com.techbank.cqrs.base.events.BaseEvent;
import com.techbank.cqrs.base.events.EventModel;
import com.techbank.cqrs.base.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Collection<BaseEvent> events, int expectedVersion) {
        var allEvents = eventStoreRepository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && allEvents.get(allEvents.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var e : events) {
            e.setVersion(++version);
            var eventModel = EventModel
                    .builder()
                    .timestamp(Instant.now().toEpochMilli())
                    .aggregateId(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .eventData(e)
                    .version(version)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                //todo: produce event to KAFKA
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventModels = eventStoreRepository.findByAggregateId(aggregateId);
        if (CollectionUtils.isEmpty(eventModels)) throw new RuntimeException("ouch..");
        return eventModels
                .stream()
                .map(EventModel::getEventData)
                .toList();
    }
}
