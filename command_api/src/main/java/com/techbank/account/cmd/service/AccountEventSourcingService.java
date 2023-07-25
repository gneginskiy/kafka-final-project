package com.techbank.account.cmd.service;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.base.aggregate.AggregateRoot;
import com.techbank.account.base.service.EventSourcingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventSourcingService implements EventSourcingService<AccountAggregate> {
    private final AccountEventStoreService accountEventStoreService;

    @Override
    public void saveAggregate(AggregateRoot aggregateRoot) {
        var aggregateId       = aggregateRoot.getId();
        var uncommitedChanges = aggregateRoot.getUncommitedChanges();
        var version           = aggregateRoot.getVersion();
        accountEventStoreService.saveEvents(aggregateId, uncommitedChanges, version);
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getAggregateById(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = accountEventStoreService.getEvents(aggregateId);
        aggregate.replayEvents(events);
        return aggregate;
    }
}
