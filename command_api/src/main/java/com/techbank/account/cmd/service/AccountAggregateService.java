package com.techbank.account.cmd.service;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.repository.AccountAggregateRepository;
import com.techbank.account.cmd.repository.EventStoreRepository;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAggregateService {
    private final EventStoreRepository eventStoreRepository;
    private final AccountAggregateRepository accountAggregateRepository;

    //todo: snapshot saver + optimistic lock
    public AccountAggregate apply(AccountOpenedEvent event) {
        return accountAggregateRepository.save(toAggregate(event));
    }

    private static AccountAggregate toAggregate(AccountOpenedEvent event) {
        return new AccountAggregate()
                .setId(event.getId())
                .setActive(true)
                .setBalance(event.getOpeningBalance())
                .setVersion(1);
    }

    public void apply(AccountFundsDepositedEvent event) {

    }

    public void apply(AccountFundsWithdrawnEvent event) {

    }

    public void apply(AccountClosedEvent event) {

    }

    public AccountAggregate getById(String id) {
        return accountAggregateRepository.findById(id).orElse(null);
    }
}
