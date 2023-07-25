package com.techbank.account.cmd.service;

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

    //todo: snapshot saver + optimistic lock
    public void apply(AccountOpenedEvent event) {

    }

    public void apply(AccountFundsDepositedEvent event) {

    }

    public void apply(AccountFundsWithdrawnEvent event) {

    }

    public void apply(AccountClosedEvent event) {

    }
}
