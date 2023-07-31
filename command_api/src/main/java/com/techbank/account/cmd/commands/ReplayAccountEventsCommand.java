package com.techbank.account.cmd.commands;

import com.techbank.account.base.command.BaseCommand;
import lombok.Data;

import java.util.UUID;

@Data
public class ReplayAccountEventsCommand extends BaseCommand {
    public ReplayAccountEventsCommand(String aggregateId) {
        super(null);
    }
}
