package com.techbank.account.cmd.domain;


import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.shared.events.AccountClosedEvent;
import com.techbank.account.shared.events.AccountOpenedEvent;
import com.techbank.account.shared.events.FundsDepositedEvent;
import com.techbank.account.shared.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean active;
    private BigDecimal balance;

    public AccountAggregate(OpenAccountCommand openCommand) {
        raiseEvent(toEvent(openCommand));
    }

    //why it's there.... todo.
    public void depositFunds(BigDecimal funds) {
        //todo: further validation
        if (funds.compareTo(BigDecimal.ZERO) < 0 || !active) throw new IllegalStateException("Illegal state");
        raiseEvent(FundsDepositedEvent.builder().id(this.aggregateId).amount(funds).build());
    }

    public void withdrawFunds(BigDecimal funds) {
        //todo: further validation
        if (funds.compareTo(balance) > 0 || !active) throw new IllegalStateException("Illegal state");
        raiseEvent(FundsWithdrawnEvent.builder().id(this.aggregateId).amount(funds).build());
    }

    public void close() {
        raiseEvent(AccountClosedEvent.builder().id(this.aggregateId).build());
    }


    @Override
    public void apply(BaseEvent event) {
        if (event instanceof AccountOpenedEvent e) {
            active = true;
            this.balance = e.getOpeningBalance();
        } else if (event instanceof AccountClosedEvent e) {
            active = false;
        } else if (event instanceof FundsDepositedEvent e) {
            balance = balance.add(e.getAmount());
        } else if (event instanceof FundsWithdrawnEvent e) {
            balance = balance.subtract(e.getAmount());
        }
        this.version = Math.max(version, event.getVersion());
    }

    //extract to a separate converter
    private static AccountOpenedEvent toEvent(OpenAccountCommand c) {
        return AccountOpenedEvent.builder()
                .id(c.getId())
                .accountHolder(c.getAccountHolder())
                .accountType(c.getAccountType())
                .openingBalance(c.getOpeningBalance())
                .createdAt(Instant.now().toEpochMilli())
                .build();
    }
}
