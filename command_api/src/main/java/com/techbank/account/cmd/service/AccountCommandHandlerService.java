package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.*;
import com.techbank.account.cmd.validation.AccountCommandValidator;
import com.techbank.account.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AccountCommandHandlerService {
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
