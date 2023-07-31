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
    private final AccountCommandValidator validator;
    private final AccountCommandToEventMapper eventMapper;
    private final AccountAggregateService aggregateService;
    private static final AtomicBoolean isReplay = new AtomicBoolean(false);

    public UUID handle(OpenAccountCommand cmd) {
        checkReplay();
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        return event.getAggregateId();
    }


    public void handle(CloseAccountCommand cmd) {
        checkReplay();
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }

    public void handle(WithdrawFundsCommand cmd) {
        checkReplay();
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }

    public void handle(DepositFundsCommand cmd) {
        checkReplay();
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
    }

    public void handle(ReplayAccountEventsCommand cmd) {
        var event = eventMapper.buildEvent(cmd);
        isReplay.set(true);
        aggregateService.apply(event);

        isReplay.set(false);
    }


    private static void checkReplay() {
        if (isReplay.get()) throw ApiError.internalServerError("Replay in progress...",null);
    }
}
