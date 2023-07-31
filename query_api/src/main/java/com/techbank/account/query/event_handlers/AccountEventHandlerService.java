package com.techbank.account.query.event_handlers;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.query.entity.AccountEntity;
import com.techbank.account.query.entity.LpeEntity;
import com.techbank.account.query.repository.AccountRepository;
import com.techbank.account.query.converter.UnifiedMapper;
import com.techbank.account.query.repository.AccountLpeRepository;
import com.techbank.account.query.util.Futility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountEventHandlerService {
    private final AccountLpeRepository accountAccountLpeRepository;
    private final AccountRepository    accountRepository;
    private final UnifiedMapper        mapper;
    private final AtomicBoolean        isReplay;

    public void handle(BaseEvent evt) {
        if (isAlreadyProcessed(evt)) return;
        if (evt instanceof AccountOpenedEvent         e) handle(e);
        if (evt instanceof AccountFundsDepositedEvent e) handle(e);
        if (evt instanceof AccountFundsWithdrawnEvent e) handle(e);
        if (evt instanceof AccountClosedEvent         e) handle(e);
        saveLastProcessedEventTs(evt);
    }

    private void handle(AccountOpenedEvent evt) {
        AccountEntity entity = toEntity(evt);
        mapper.setFtsIndexValue(entity);
        accountRepository.save(entity);
    }

    private void handle(AccountClosedEvent evt) {
        var entity = accountRepository.findById(evt.getId()).orElseThrow().setActive(false);
        mapper.setFtsIndexValue(entity);
        accountRepository.save(entity);
    }

    private void handle(AccountFundsDepositedEvent evt) {
        var entity = accountRepository.findById(evt.getId()).orElseThrow();
        entity.setBalance(entity.getBalance().add(evt.getAmount()));
        mapper.setFtsIndexValue(entity);
        accountRepository.save(entity);
    }

    private void handle(AccountFundsWithdrawnEvent evt) {
        var entity = accountRepository.findById(evt.getId()).orElseThrow();
        entity.setBalance(entity.getBalance().subtract(evt.getAmount()));
        mapper.setFtsIndexValue(entity);
        accountRepository.save(entity);
    }

    private static AccountEntity toEntity(AccountOpenedEvent evt) {
        return new AccountEntity()
                .setAccountHolder(evt.getAccountHolder())
                .setAccountType(evt.getAccountType())
                .setCreatedAt(evt.getTimestamp())
                .setActive(true)
                .setBalance(evt.getOpeningBalance())
                .setId(evt.getAggregateId());
    }

    private void saveLastProcessedEventTs(BaseEvent evt) {
        accountAccountLpeRepository.save(LpeEntity.of(evt));
    }

    private boolean isAlreadyProcessed(BaseEvent evt) {
        var currentLpe = accountAccountLpeRepository.findById(0L).orElse(LpeEntity.DEFAULT).getTs();
        if (currentLpe > evt.getTimestamp()) {
            log.info("Skipping event " + evt.getClass() + " " + Futility.toJson(evt));
            return true;
        }
        return false;
    }
}
