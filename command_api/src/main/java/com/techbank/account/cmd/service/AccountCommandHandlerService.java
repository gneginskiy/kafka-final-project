package com.techbank.account.cmd.service;

import com.techbank.account.base.service.EventSourcingService;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.validation.AccountCommandValidator;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandlerService {
    private final EventSourcingService<AccountAggregate> eventSourcingService;
    private final AccountCommandValidator validator;
    private final AccountCommandToEventMapper eventMapper;
    private final AccountAggregateService aggregateService;
    private final AccountEventSender eventSender;

    public void handle(OpenAccountCommand cmd) {
        //todo: complete:
        validator.validate(cmd);
        AccountOpenedEvent event = eventMapper.getEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);

//        eventSourcingService.saveAggregate(new AccountAggregate(cmd));
    }

    public void handle(CloseAccountCommand cmd) {
        //todo: complete
        validator.validate(cmd);
        AccountClosedEvent event = eventMapper.getEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);

        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
        aggregate.close();
        eventSourcingService.saveAggregate(aggregate);
    }

    public void handle(WithdrawFundsCommand cmd) {
        //todo: complete
        validator.validate(cmd);
        AccountFundsWithdrawnEvent event = eventMapper.getEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);

        validator.validate(cmd);
        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
        aggregate.withdrawFunds(cmd.getAmount());
        eventSourcingService.saveAggregate(aggregate);
    }

    public void handle(DepositFundsCommand cmd) {
        //todo: complete
        validator.validate(cmd);
        AccountFundsDepositedEvent event = eventMapper.getEvent(cmd);
        aggregateService.apply(event);
        eventSender.send(event);

        //        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
//        aggregate.depositFunds(cmd.getAmount());
//        eventSourcingService.saveAggregate(aggregate);
    }
}
