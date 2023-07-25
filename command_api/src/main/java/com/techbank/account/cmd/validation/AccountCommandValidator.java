package com.techbank.account.cmd.validation;

import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCommandValidator {

    public void validate(DepositFundsCommand cmd) {

    }

    public void validate(WithdrawFundsCommand cmd) {

    }

    public void validate(CloseAccountCommand cmd) {

    }

    public void validate(OpenAccountCommand cmd) {

    }
}
