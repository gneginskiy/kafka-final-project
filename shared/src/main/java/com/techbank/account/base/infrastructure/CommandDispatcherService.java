package com.techbank.account.base.infrastructure;


import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.base.command.CommandHandlerMethod;

public interface CommandDispatcherService<T extends BaseCommand> {
    void registerCommand(Class<T> type, CommandHandlerMethod<T> handler);
    void send(T command);
}
