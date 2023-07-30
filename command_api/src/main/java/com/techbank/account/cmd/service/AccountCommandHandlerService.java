package com.techbank.account.cmd.service;

import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.validation.AccountCommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountCommandHandlerService {
    private final AccountCommandValidator validator;
    private final AccountCommandToEventMapper eventMapper;
    private final AccountAggregateService aggregateService;
    private final AccountEventSender eventSender;

    public UUID handle(OpenAccountCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);
        return event.getAggregateId();
    }

    public void handle(CloseAccountCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);
    }

    public void handle(WithdrawFundsCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);
    }

    public void handle(DepositFundsCommand cmd) {
        validator.validate(cmd);
        var event = eventMapper.buildEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);
    }
}
