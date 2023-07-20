package com.techbank.account.cmd.api.commands;

import com.techbank.account.shared.dto.AccountType;
import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositFundsCommand extends BaseCommand {
    private BigDecimal  amount;
}
