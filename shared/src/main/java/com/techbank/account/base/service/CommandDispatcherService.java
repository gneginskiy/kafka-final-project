package com.techbank.account.base.service;


import com.techbank.account.base.command.BaseCommand;

public interface CommandDispatcherService {
    <T extends BaseCommand> void send(T command);
}
