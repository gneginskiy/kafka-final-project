package com.techbank.account.cmd;

import com.techbank.account.base.service.CommandDispatcherService;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.service.AccountCommandHandlerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CommandApplication {
    private final CommandDispatcherService cmdDispatcher;
    private final AccountCommandHandlerService handler;

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }


	//todo: extract to configuration.
    @PostConstruct
    public void registerCommandHandlers() {
		cmdDispatcher.registerCommand(OpenAccountCommand.class,   handler::handle);
		cmdDispatcher.registerCommand(CloseAccountCommand.class,  handler::handle);
		cmdDispatcher.registerCommand(DepositFundsCommand.class,  handler::handle);
		cmdDispatcher.registerCommand(WithdrawFundsCommand.class, handler::handle);
    }

}
