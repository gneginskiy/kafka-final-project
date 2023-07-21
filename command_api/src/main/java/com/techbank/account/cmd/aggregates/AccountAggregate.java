package com.techbank.account.cmd.aggregates;


import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.events.AccountClosedEventDto;
import com.techbank.account.events.AccountOpenedEventDto;
import com.techbank.account.events.FundsDepositedEventDto;
import com.techbank.account.events.FundsWithdrawnEventDto;
import com.techbank.account.base.aggregate.AggregateRoot;
import com.techbank.account.base.events.BaseEventDto;
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
        raiseEvent(FundsDepositedEventDto.builder().id(this.aggregateId).amount(funds).build());
    }

    public void withdrawFunds(BigDecimal funds) {
        //todo: further validation
        if (funds.compareTo(balance) > 0 || !active) throw new IllegalStateException("Illegal state");
        raiseEvent(FundsWithdrawnEventDto.builder().id(this.aggregateId).amount(funds).build());
    }

    public void close() {
        raiseEvent(AccountClosedEventDto.builder().id(this.aggregateId).build());
    }


    @Override
    public void apply(BaseEventDto event) {
        if (event instanceof AccountOpenedEventDto e) {
            active = true;
            this.balance = e.getOpeningBalance();
        } else if (event instanceof AccountClosedEventDto e) {
            active = false;
        } else if (event instanceof FundsDepositedEventDto e) {
            balance = balance.add(e.getAmount());
        } else if (event instanceof FundsWithdrawnEventDto e) {
            balance = balance.subtract(e.getAmount());
        }
        this.version = Math.max(version, event.getVersion());
    }

    //extract to a separate converter
    private static AccountOpenedEventDto toEvent(OpenAccountCommand c) {
        return AccountOpenedEventDto.builder()
                .id(c.getId())
                .accountHolder(c.getAccountHolder())
                .accountType(c.getAccountType())
                .openingBalance(c.getOpeningBalance())
                .createdAt(Instant.now().toEpochMilli())
                .build();
    }
}
