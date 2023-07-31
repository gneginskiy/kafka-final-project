package com.techbank.account.cmd.service;

import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.cmd.commands.*;
import com.techbank.account.dto.events.*;
import com.techbank.account.dto.events.admin.AccountsReplayCompletedEvent;
import com.techbank.account.dto.events.admin.AccountsReplayStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountCommandToEventMapper {
    private final MongoSequenceGeneratorService sequenceGeneratorService;

    public AccountFundsDepositedEvent buildEvent(DepositFundsCommand cmd) {
        var evt = new AccountFundsDepositedEvent().setAmount(cmd.getAmount());
        return setBaseEventFields(evt,cmd);
    }

    public AccountFundsWithdrawnEvent buildEvent(WithdrawFundsCommand cmd) {
        var evt = new AccountFundsWithdrawnEvent().setAmount(cmd.getAmount());
        return setBaseEventFields(evt,cmd);
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
        return evt.setId(sequenceGeneratorService.generateEventIdSeq())
                .setAggregateId(cmd.getAggregateId())
                .setTimestamp(Instant.now().toEpochMilli());
    }
}
