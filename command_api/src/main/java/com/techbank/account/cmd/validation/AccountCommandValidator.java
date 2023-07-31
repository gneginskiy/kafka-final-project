package com.techbank.account.cmd.validation;

import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.service.AccountAggregateService;
import com.techbank.account.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.techbank.account.cmd.validation.AccountValidationAssertions.*;
import static com.techbank.account.cmd.validation.ValidationAssertions.*;

@Service
@RequiredArgsConstructor
public class AccountCommandValidator {
    private final AccountAggregateService aggregateService;

    public void validate(DepositFundsCommand cmd) {
        checkReplayIsNotInProgress();
        checkIdPresent(cmd);
        checkAmountGtZero(cmd);
        var aggregate = aggregateService.getById(cmd.getAggregateId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
    }

    public void validate(WithdrawFundsCommand cmd) {
        checkReplayIsNotInProgress();

        checkIdPresent(cmd);
        checkAmountGtZero(cmd);
        var aggregate = aggregateService.getById(cmd.getAggregateId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
        checkWithdrawalAllowed(aggregate, cmd);
    }

    public void validate(CloseAccountCommand cmd) {
        checkReplayIsNotInProgress();
        checkIdPresent(cmd);
        var aggregate = aggregateService.getById(cmd.getAggregateId());
        checkAccountPresent(cmd, aggregate);
        checkAccountActive(aggregate);
    }

    public void validate(OpenAccountCommand cmd) {
        checkReplayIsNotInProgress();
        checkFieldNotEmpty(cmd.getAccountType(), cmd, "accountType");
        checkFieldNotEmpty(cmd.getAccountHolder(), cmd, "accountHolder");
        checkFieldGteZero(cmd.getOpeningBalance(), cmd, "openingBalance");
    }

    private void checkReplayIsNotInProgress() {
        if (aggregateService.isReplay()) throw ApiError.internalServerError("replay is in progress", null);
    }
}
