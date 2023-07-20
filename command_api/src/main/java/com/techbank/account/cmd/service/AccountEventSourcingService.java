package com.techbank.account.cmd.service;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.base.domain.AggregateRoot;
import com.techbank.account.base.handler.EventSourcingHandler;
import com.techbank.account.base.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventSourcingService implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        var aggregateId       = aggregateRoot.getAggregateId();
        var uncommitedChanges = aggregateRoot.getUncommitedChanges();
        var version           = aggregateRoot.getVersion();
        eventStore.saveEvents(aggregateId, uncommitedChanges, version);
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(aggregateId);
        aggregate.replayEvents(events);
        return aggregate;
    }
}
