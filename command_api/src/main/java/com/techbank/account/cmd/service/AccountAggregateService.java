package com.techbank.account.cmd.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.base.events.EventEntity;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.*;
import com.techbank.account.exception.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readTimestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountAggregateService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateRepository accountAggregateRepository;
    private final AccountEventSender eventSender;
    private final AtomicBoolean isReplayInProgress = new AtomicBoolean(false);

    public void apply(BaseEvent evt) {
        if      (evt instanceof AccountOpenedEvent           e) apply(e);
        else if (evt instanceof AccountFundsDepositedEvent   e) apply(e);
        else if (evt instanceof AccountFundsWithdrawnEvent   e) apply(e);
        else if (evt instanceof AccountClosedEvent           e) apply(e);
        else throw ApiError.internalServerError("No handler for evt " + evt.getClass(), null);
        eventSender.send(evt);
    }

    private void apply(AccountOpenedEvent event) {
        AccountAggregate aggregate = accountAggregateRepository.save(buildNewAggregate(event));
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    private void apply(AccountFundsDepositedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().add(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    private void apply(AccountFundsWithdrawnEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().subtract(event.getAmount()));
        accountAggregateRepository.save(aggregate);
        eventsRepository.save(toEventEntity(event, aggregate));
    }

    private void apply(AccountClosedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setActive(false);
        accountAggregateRepository.save(aggregate);
        if (!isReplay()) eventsRepository.save(toEventEntity(event, aggregate));
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

    private EventEntity toEventEntity(BaseEvent event, AccountAggregate aggregate) {
        return new EventEntity()
                .setId(event.getId())
                .setAggregateId(aggregate.getId())
                .setAggregateType(aggregate.getClass().getSimpleName())
                .setEventType(event.getClass().getSimpleName())
                .setEventData(event)
                .setTimestamp(readTimestamp(event));
    }

    public boolean isReplay() {
        return isReplayInProgress.get();
    }

    public void replayStart() {
        log.info("Replay started at "+ LocalDateTime.now());
        isReplayInProgress.set(true);
        accountAggregateRepository.deleteAll();
    }

    public void replayEnd() {
        isReplayInProgress.set(false);
        log.info("Replay completed at "+ LocalDateTime.now());
    }
}
