package com.techbank.account.query.event_handlers;

import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.query.entity.AccountEntity;
import com.techbank.account.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountEventHandler {
    private final AccountRepository accountRepository;

    public void handle(AccountOpenedEvent evt) {
        accountRepository.save(toEntity(evt));
    }

    public void handle(AccountClosedEvent evt) {
        var account = accountRepository.findById(evt.getId()).orElseThrow().setActive(false);
        accountRepository.save(account);
    }


    public void handle(AccountFundsDepositedEvent evt) {
        var account = accountRepository.findById(evt.getId()).orElseThrow();
        account.setBalance(account.getBalance().add(evt.getAmount()));
        accountRepository.save(account);
    }

    public void handle(AccountFundsWithdrawnEvent evt) {
        var account = accountRepository.findById(evt.getId()).orElseThrow();
        account.setBalance(account.getBalance().subtract(evt.getAmount()));
        accountRepository.save(account);
    }

    private static AccountEntity toEntity(AccountOpenedEvent evt) {
        return new AccountEntity()
                .setAccountHolder(evt.getAccountHolder())
                .setAccountType(evt.getAccountType())
                .setCreatedAt(evt.getCreatedAt())
                .setActive(true)
                .setBalance(evt.getOpeningBalance())
                .setId(evt.getAccountHolder());
    }
}
