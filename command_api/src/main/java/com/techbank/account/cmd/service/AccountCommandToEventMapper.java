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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCommandToEventMapper {
    public AccountFundsDepositedEvent buildEvent(DepositFundsCommand cmd) {
        return new AccountFundsDepositedEvent()
                .setId(cmd.getId())
                .setAmount(cmd.getAmount())
                .setTimestamp(Instant.now().toEpochMilli());
    }

    public AccountFundsWithdrawnEvent buildEvent(WithdrawFundsCommand cmd) {
        return new AccountFundsWithdrawnEvent()
                .setId(cmd.getId())
                .setAmount(cmd.getAmount())
                .setTimestamp(Instant.now().toEpochMilli());
    }

    public AccountClosedEvent buildEvent(CloseAccountCommand cmd) {
        return new AccountClosedEvent()
                .setId(cmd.getId())
                .setTimestamp(Instant.now().toEpochMilli());
    }

    public AccountOpenedEvent buildEvent(OpenAccountCommand cmd) {
        return new AccountOpenedEvent()
                .setId(UUID.randomUUID().toString())
                .setAccountHolder(cmd.getAccountHolder())
                .setAccountType(cmd.getAccountType())
                .setOpeningBalance(cmd.getOpeningBalance())
                .setTimestamp(Instant.now().toEpochMilli());
    }
}
