package com.techbank.account.cmd.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.base.events.EventEntity;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.commands.ReplayAccountEventsCommand;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readId;
import static com.techbank.account.cmd.validation.AccountReflectUtil.readTimestamp;

@Service
@RequiredArgsConstructor
public class AccountAggregateService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateRepository accountAggregateRepository;
    private final AccountEventSender eventSender;

    public void apply(AccountOpenedEvent event) {
        AccountAggregate aggregate = accountAggregateRepository.save(buildNewAggregate(event));
        eventsRepository.save(toEventEntity(event, aggregate));
        eventSender.send(event);
    }

    public void apply(AccountFundsDepositedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().add(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
        eventSender.send(event);
    }

    public void apply(AccountFundsWithdrawnEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().subtract(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
        eventSender.send(event);
    }

    public void apply(AccountClosedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setActive(false);
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
        eventSender.send(event);
    }

    private AccountAggregate fetchAggregate(BaseEvent event) {
        return getById(event.getAggregateId());
    }

    public AccountAggregate getById(UUID id) {
        return accountAggregateRepository.findById(id).orElse(null);
    }

    private static AccountAggregate buildNewAggregate(AccountOpenedEvent event) {
        return new AccountAggregate()
                .setId(event.getAggregateId())
                .setActive(true)
                .setBalance(event.getOpeningBalance())
                .setVersion(0);
    }

    private static EventEntity toEventEntity(BaseEvent event, AccountAggregate aggregate) {
        return new EventEntity()
                .setAggregateId(aggregate.getId())
                .setAggregateType(aggregate.getClass().getSimpleName())
                .setEventType(event.getClass().getSimpleName())
                .setEventData(event)
                .setTimestamp(readTimestamp(event));
    }

    public void apply(AccountsReplayStartedEvent evt) {

    }
}
