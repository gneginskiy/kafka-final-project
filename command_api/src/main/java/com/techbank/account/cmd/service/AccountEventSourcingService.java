package com.techbank.account.cmd.service;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.base.aggregate.AggregateRoot;
import com.techbank.account.base.service.EventSourcingService;
import com.techbank.account.base.infrastructure.EventStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventSourcingService implements EventSourcingService<AccountAggregate> {
    private final EventStoreService eventStoreService;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        var aggregateId       = aggregateRoot.getAggregateId();
        var uncommitedChanges = aggregateRoot.getUncommitedChanges();
        var version           = aggregateRoot.getVersion();
        eventStoreService.saveEvents(aggregateId, uncommitedChanges, version);
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = eventStoreService.getEvents(aggregateId);
        aggregate.replayEvents(events);
        return aggregate;
    }
}
