package com.techbank.account.cmd.service;

import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.base.events.BaseEvent;
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
    private static final TimeBasedEpochGenerator uuidGenerator = new TimeBasedEpochGenerator(null);

    public AccountFundsDepositedEvent buildEvent(DepositFundsCommand cmd) {
        return new AccountFundsDepositedEvent()
                .setAmount(cmd.getAmount())
                //base event fields
                .setAggregateId(cmd.getAggregateId())
                .setTimestamp(Instant.now().toEpochMilli())
                .setId(uuidGenerator.generate());
    }

    public AccountFundsWithdrawnEvent buildEvent(WithdrawFundsCommand cmd) {
        return new AccountFundsWithdrawnEvent()
                .setAmount(cmd.getAmount())
                //base event fields
                .setId(uuidGenerator.generate())
                .setAggregateId(cmd.getAggregateId())
                .setTimestamp(Instant.now().toEpochMilli());
    }

    public AccountClosedEvent buildEvent(CloseAccountCommand cmd) {
        var evt = new AccountClosedEvent();
        return setBaseEventFields(evt,cmd);
    }

    public AccountOpenedEvent buildEvent(OpenAccountCommand cmd) {
        var evt = new AccountOpenedEvent()
                .setAccountHolder(cmd.getAccountHolder())
                .setAccountType(cmd.getAccountType())
                .setOpeningBalance(cmd.getOpeningBalance());
        return setBaseEventFields(evt,cmd);
    }

    private <E extends BaseEvent, C extends BaseCommand> E setBaseEventFields(E evt, C cmd) {
        return evt.setId(uuidGenerator.generate())
                .setAggregateId(cmd.getAggregateId())
                .setTimestamp(Instant.now().toEpochMilli());
    }
}
