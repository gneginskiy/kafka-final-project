package com.techbank.account.cmd.service;

import com.techbank.cqrs.base.commands.BaseCommand;
import com.techbank.cqrs.base.commands.CommandHandlerMethod;
import com.techbank.cqrs.base.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class AccountCommandDispatcherService<T extends BaseCommand> implements CommandDispatcher<T> {
    private final Map<Class<T>, List<CommandHandlerMethod<T>>> map = new ConcurrentHashMap<>(); //todo DI;

    @Override
    public void registerCommand(Class<T> type, CommandHandlerMethod<T> handler) {
        map.computeIfAbsent(type, v -> new ArrayList<>()).add(handler);
    }

    @Override
    public void send(T command) {
        map.getOrDefault(command.getClass(), List.of()).forEach(handler -> handler.handle(command));
    }
}
