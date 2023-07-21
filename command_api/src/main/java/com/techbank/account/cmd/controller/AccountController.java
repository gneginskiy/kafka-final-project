package com.techbank.account.cmd.controller;

import com.techbank.account.base.service.CommandDispatcherService;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AccountController.API_URL)
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    public static final String API_URL = "/api/v1/controller";

    private final CommandDispatcherService commandDispatcher;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody OpenAccountCommand cmd) {
        cmd.setId(UUID.randomUUID().toString());
        commandDispatcher.send(cmd);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{id}/close", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody CloseAccountCommand cmd) {
        commandDispatcher.send(cmd);
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/{id}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody WithdrawFundsCommand cmd) {
        commandDispatcher.send(cmd);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody DepositFundsCommand cmd) {
        commandDispatcher.send(cmd);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e);
    }
}
