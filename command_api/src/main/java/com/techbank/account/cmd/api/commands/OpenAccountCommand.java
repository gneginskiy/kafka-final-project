package com.techbank.account.cmd.api.commands;

import com.techbank.account.shared.dto.AccountType;
import com.techbank.cqrs.base.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String      accountHolder;
    private AccountType accountType;
    private BigDecimal  openingBalance;
}
