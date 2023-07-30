package com.techbank.account.cmd.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.base.events.EventEntity;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readId;
import static com.techbank.account.cmd.validation.AccountReflectUtil.readTimestamp;

@Service
@RequiredArgsConstructor
public class AccountAggregateService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateRepository accountAggregateRepository;

    public void apply(AccountOpenedEvent event) {
        AccountAggregate aggregate = accountAggregateRepository.save(buildNewAggregate(event));
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    public void apply(AccountFundsDepositedEvent event) {
        var aggregate = getById(event.getId());
        aggregate.setBalance(aggregate.getBalance().add(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    public void apply(AccountFundsWithdrawnEvent event) {
        var aggregate = getById(event.getId());
        aggregate.setBalance(aggregate.getBalance().subtract(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    public void apply(AccountClosedEvent event) {
        var aggregate = getById(event.getId());
        aggregate.setActive(false);
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    public AccountAggregate getById(String id) {
        return accountAggregateRepository.findById(id).orElse(null);
    }

    private static AccountAggregate buildNewAggregate(AccountOpenedEvent event) {
        return new AccountAggregate()
                .setId(event.getId())
                .setActive(true)
                .setBalance(event.getOpeningBalance())
                .setVersion(0);
    }

    private static EventEntity toEventEntity(BaseEvent event, AccountAggregate aggregate) {
        return new EventEntity()
                .setAggregateId(readId(aggregate))
                .setAggregateType(aggregate.getClass().getSimpleName())
                .setEventType(event.getClass().getSimpleName())
                .setEventData(event)
                .setTimestamp(readTimestamp(event));
    }
}
