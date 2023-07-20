package com.techbank.cqrs.base.infrastructure;


import com.techbank.cqrs.base.commands.BaseCommand;
import com.techbank.cqrs.base.commands.CommandHandlerMethod;

public interface CommandDispatcher <T extends BaseCommand> {
    void registerCommand(Class<T> type, CommandHandlerMethod<T> handler);
    void send(T command);
}
