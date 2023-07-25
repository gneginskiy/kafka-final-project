package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountCommandToEventMapper {
    public AccountFundsDepositedEvent getEvent(DepositFundsCommand cmd) {
        return new AccountFundsDepositedEvent()
                .setId(cmd.getId())
                .setAmount(cmd.getAmount())
                .setAmount(cmd.getAmount())
                .setCreatedAt(Instant.now().toEpochMilli());
    }

    public AccountFundsWithdrawnEvent getEvent(WithdrawFundsCommand cmd) {
        return new AccountFundsWithdrawnEvent()
                .setId(cmd.getId())
                .setAmount(cmd.getAmount())
                .setCreatedAt(Instant.now().toEpochMilli());
    }

    public AccountClosedEvent getEvent(CloseAccountCommand cmd) {
        return new AccountClosedEvent()
                .setId(cmd.getId())
                .setCreatedAt(Instant.now().toEpochMilli());
    }

    public AccountOpenedEvent getEvent(OpenAccountCommand cmd) {
        return new AccountOpenedEvent()
                .setId(cmd.getId())
                .setAccountHolder(cmd.getAccountHolder())
                .setAccountType(cmd.getAccountType())
                .setOpeningBalance(cmd.getOpeningBalance())
                .setCreatedAt(Instant.now().toEpochMilli());
    }
}
