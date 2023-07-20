package com.techbank.account.cmd.service;

import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.base.service.CommandHandlerMethod;
import com.techbank.account.base.service.CommandDispatcherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class AccountCommandDispatcherServiceService<T extends BaseCommand> implements CommandDispatcherService<T> {
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
