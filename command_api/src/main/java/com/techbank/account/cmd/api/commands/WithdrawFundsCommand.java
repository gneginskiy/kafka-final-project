package com.techbank.account.cmd.api.commands;

import com.techbank.cqrs.base.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal  amount;
}
