package com.techbank.account.cmd.service;

import com.techbank.account.base.command.BaseCommand;
import com.techbank.account.base.service.CommandHandlerMethod;
import com.techbank.account.base.service.CommandDispatcherService;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class AccountCommandDispatcherService implements CommandDispatcherService {
    private final AccountCommandHandlerService handler;

    @Override
    public void send(BaseCommand command) {
        if      (command instanceof OpenAccountCommand   cmd) handler.handle(cmd);
        else if (command instanceof CloseAccountCommand  cmd) handler.handle(cmd);
        else if (command instanceof DepositFundsCommand  cmd) handler.handle(cmd);
        else if (command instanceof WithdrawFundsCommand cmd) handler.handle(cmd);
        else throw new RuntimeException("unknown command type "+command.getClass());
    }
}
