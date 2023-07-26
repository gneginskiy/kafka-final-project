package com.techbank.account.cmd.validation;

import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readAmount;
import static com.techbank.account.cmd.validation.AccountReflectUtil.readId;
import static com.techbank.account.cmd.validation.ValidationAssertions.*;
import static com.techbank.account.cmd.validation.ValidationAssertions.checkTrue;

public class AccountValidationAssertions {
    static void checkAmountGtZero(BaseCommand cmd) {
        checkFieldGteZero(readAmount(cmd), cmd, "amount");
    }

    static void checkAccountActive(AccountAggregate aggregate) {
        checkTrue(aggregate.isActive(), aggregate, "Account is not active");
    }

    static void checkIdPresent(BaseCommand cmd) {
        checkFieldNotEmpty(readId(cmd), cmd, "id");
    }
    static void checkIdNotPresent(BaseCommand cmd) {
        checkFieldEmpty(readId(cmd), cmd, "id");
    }

    static void checkAccountPresent(BaseCommand cmd, AccountAggregate aggregate) {
        checkTrue(aggregate != null, cmd, "Account is not present");
    }

    static void checkWithdrawalAllowed(AccountAggregate aggregate, WithdrawFundsCommand cmd) {
        boolean accountIsSufficient = aggregate.getBalance().compareTo(cmd.getAmount()) > 0;
        String msg = "Account balance is " + aggregate.getBalance() + " whereas you're trying to withdraw" + cmd.getAmount();
        checkTrue(accountIsSufficient, aggregate, msg);
    }
}
