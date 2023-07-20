package com.techbank.cqrs.core.infrastructure;


import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher <T extends BaseCommand> {
    void registerCommand(Class<T> type, CommandHandlerMethod<T> handler);
    void send(T command);
}
