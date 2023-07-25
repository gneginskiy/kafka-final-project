package com.techbank.account.cmd.aggregates;


import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.dto.events.AccountClosedEvent;
import com.techbank.account.dto.events.AccountOpenedEvent;
import com.techbank.account.dto.events.AccountFundsDepositedEvent;
import com.techbank.account.dto.events.AccountFundsWithdrawnEvent;
import com.techbank.account.base.aggregate.AggregateRoot;
import com.techbank.account.base.events.BaseEvent;
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
        raiseEvent(AccountFundsDepositedEvent.builder().id(this.aggregateId).amount(funds).build());
    }

    public void withdrawFunds(BigDecimal funds) {
        //todo: further validation
        if (funds.compareTo(balance) > 0 || !active) throw new IllegalStateException("Illegal state");
        raiseEvent(AccountFundsWithdrawnEvent.builder().id(this.aggregateId).amount(funds).build());
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
        } else if (event instanceof AccountFundsDepositedEvent e) {
            balance = balance.add(e.getAmount());
        } else if (event instanceof AccountFundsWithdrawnEvent e) {
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
