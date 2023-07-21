package com.techbank.account.cmd.service;

import com.techbank.account.base.service.EventSourcingService;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandHandlerService {
    private final EventSourcingService<AccountAggregate> eventSourcingService;

    public void handle(OpenAccountCommand cmd) {
        eventSourcingService.saveAggregate(new AccountAggregate(cmd));
    }

    public void handle(CloseAccountCommand cmd) {
        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
        aggregate.close();
        eventSourcingService.saveAggregate(aggregate);
    }

    public void handle(WithdrawFundsCommand cmd) {
        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
        aggregate.withdrawFunds(cmd.getAmount());
        eventSourcingService.saveAggregate(aggregate);
    }

    public void handle(DepositFundsCommand cmd) {
        var aggregate = eventSourcingService.getAggregateById(cmd.getId());
        aggregate.depositFunds(cmd.getAmount());
        eventSourcingService.saveAggregate(aggregate);
    }
}
