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

@Service
@RequiredArgsConstructor
public class AccountCommandToEventMapper {
    public AccountFundsDepositedEvent getEvent(DepositFundsCommand cmd) {
        return null;
    }

    public AccountFundsWithdrawnEvent getEvent(WithdrawFundsCommand cmd) {
        return null;
    }

    public AccountClosedEvent getEvent(CloseAccountCommand cmd) {
        return null;
    }

    public AccountOpenedEvent getEvent(OpenAccountCommand cmd) {
        return null;
    }
}
