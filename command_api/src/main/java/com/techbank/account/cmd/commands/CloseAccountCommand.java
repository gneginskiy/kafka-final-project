package com.techbank.account.cmd.commands;

import com.techbank.account.base.command.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String aggregateId) {
        super(aggregateId);
    }
}
