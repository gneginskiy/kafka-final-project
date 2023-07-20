package com.techbank.account.base.service;

import com.techbank.account.base.command.BaseCommand;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handle(T command);
}
