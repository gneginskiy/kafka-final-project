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
public class AccountCommandDispatcherService implements CommandDispatcherService {
    private final Map<Class, List<CommandHandlerMethod>> map = new ConcurrentHashMap<>(); //todo DI;

    @Override
    public  void registerCommand(Class type, CommandHandlerMethod handler) {
        map.computeIfAbsent(type, v -> new ArrayList<>()).add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        map.getOrDefault(command.getClass(), List.of()).forEach(handler -> handler.handle(command));
    }
}
