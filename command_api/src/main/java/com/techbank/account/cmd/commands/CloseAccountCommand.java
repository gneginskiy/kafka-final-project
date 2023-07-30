package com.techbank.account.cmd.commands;

import com.techbank.account.base.command.BaseCommand;
import lombok.Data;

import java.util.UUID;

@Data
public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(UUID aggregateId) {
        super(aggregateId);
    }
}
