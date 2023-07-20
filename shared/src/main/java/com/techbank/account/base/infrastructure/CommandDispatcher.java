package com.techbank.account.base.infrastructure;


import com.techbank.account.base.commands.BaseCommand;
import com.techbank.account.base.commands.CommandHandlerMethod;

public interface CommandDispatcher <T extends BaseCommand> {
    void registerCommand(Class<T> type, CommandHandlerMethod<T> handler);
    void send(T command);
}
