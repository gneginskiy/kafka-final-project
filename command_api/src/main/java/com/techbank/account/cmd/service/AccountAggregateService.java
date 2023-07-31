package com.techbank.account.cmd.service;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.base.events.EventEntity;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.*;
import com.techbank.account.dto.events.admin.AccountsReplayCompletedEvent;
import com.techbank.account.dto.events.admin.AccountsReplayStartedEvent;
import com.techbank.account.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readTimestamp;

@Service
@RequiredArgsConstructor
public class AccountAggregateService {
    private final EventStoreRepository eventsRepository;
    private final AccountAggregateRepository accountAggregateRepository;
    private final AccountEventSender eventSender;


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
        if (eventsRepository.existsById(event.getId())) {
            eventsRepository.save(toEventEntity(event, aggregate));
        }
    }


    private void apply(AccountFundsDepositedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().add(event.getAmount()));
        accountAggregateRepository.save(aggregate);
    }

    private void apply(AccountFundsWithdrawnEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setBalance(aggregate.getBalance().subtract(event.getAmount()));
        accountAggregateRepository.save(aggregate);
    }

    private void apply(AccountClosedEvent event) {
        var aggregate = fetchAggregate(event);
        aggregate.setActive(false);
        accountAggregateRepository.save(aggregate);
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


    private boolean isReplay() {
        return false;
    }

}
