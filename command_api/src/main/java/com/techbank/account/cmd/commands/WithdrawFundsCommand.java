package com.techbank.account.cmd.commands;

import com.techbank.account.base.command.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal  amount;
}
