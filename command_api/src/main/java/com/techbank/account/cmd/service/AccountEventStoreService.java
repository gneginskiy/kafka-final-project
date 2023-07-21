package com.techbank.account.cmd.service;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.cmd.exceptions.ConcurrencyException;
import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.base.events.EventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountEventStoreService {
    private final EventStoreRepository eventStoreRepository;
    private final AccountEventProducer accountEventProducer;

    public void saveEvents(String aggregateId, Collection<BaseEvent> events, int expectedVersion) {
        var allEvents = eventStoreRepository.findByAggregateId(aggregateId);
        if (expectedVersion != -1 && allEvents.get(allEvents.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var e : events) {
            e.setVersion(++version);
            var persistedEvent = eventStoreRepository.save(toEventEntity(aggregateId, version, e));
            if (!persistedEvent.getId().isEmpty()) {
                accountEventProducer.produce(e.getClass().getSimpleName(), e);
            }
        }
    }
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventModels = eventStoreRepository.findByAggregateId(aggregateId);
        if (CollectionUtils.isEmpty(eventModels)) throw new RuntimeException("ouch..");
        return eventModels
                .stream()
                .map(EventEntity::getEventData)
                .toList();
    }


    private static EventEntity toEventEntity(String aggregateId, int version, BaseEvent e) {
        return EventEntity
                .builder()
                .timestamp(Instant.now().toEpochMilli())
                .aggregateId(aggregateId)
                .aggregateType(AccountAggregate.class.getTypeName())
                .eventData(e)
                .version(version)
                .build();
    }


}