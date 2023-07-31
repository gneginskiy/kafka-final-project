package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.*;
import com.techbank.account.cmd.validation.AccountCommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCommandDispatcher {
    private final AccountCommandValidator     validator;
    private final AccountCommandToEventMapper eventMapper;
    private final AccountAggregateService     aggregateService;
    private final AccountReplayService        accountReplayService;

    public void handle(ReplayAccountEventsCommand cmd) {
        accountReplayService.runReplay(cmd);
    }

    public UUID handle(OpenAccountCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        return event.getAggregateId();
    }


    public void handle(CloseAccountCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }

    public void handle(WithdrawFundsCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }

    public void handle(DepositFundsCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }
}
