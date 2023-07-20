package com.techbank.account.cmd.commands;

import com.techbank.account.base.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal  amount;
}
