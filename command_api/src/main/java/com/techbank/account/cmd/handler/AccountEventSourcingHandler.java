package com.techbank.account.cmd.handler;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handler.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
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
