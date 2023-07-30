package com.techbank.account.cmd.validation;

import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.cmd.aggregates.AccountAggregate;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.service.AccountAggregateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.techbank.account.cmd.validation.AccountValidationAssertions.*;
import static com.techbank.account.cmd.validation.ValidationAssertions.*;
import static com.techbank.account.cmd.validation.AccountReflectUtil.readAmount;
import static com.techbank.account.cmd.validation.AccountReflectUtil.readId;

@Service
@RequiredArgsConstructor
public class AccountCommandValidator {
    private final AccountAggregateService aggregateService;

    public void validate(DepositFundsCommand cmd) {
        checkIdPresent(cmd);
        checkAmountGtZero(cmd);
        var aggregate = aggregateService.getById(cmd.getId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
    }

    public void validate(WithdrawFundsCommand cmd) {
        checkIdPresent(cmd);
        checkAmountGtZero(cmd);
        var aggregate = aggregateService.getById(cmd.getId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
        checkWithdrawalAllowed(aggregate, cmd);
    }

    public void validate(CloseAccountCommand cmd) {
        checkIdPresent(cmd);
        var aggregate = aggregateService.getById(cmd.getId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
    }

    public void validate(OpenAccountCommand cmd) {
        checkIdNotPresent(cmd);
        checkFieldNotEmpty(cmd.getAccountType(), cmd, "accountType");
        checkFieldNotEmpty(cmd.getAccountHolder(), cmd, "accountHolder");
        checkFieldGteZero(cmd.getOpeningBalance(), cmd, "openingBalance");
    }
}
