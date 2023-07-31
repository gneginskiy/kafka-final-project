package com.techbank.account.cmd.controller;

import com.techbank.account.cmd.commands.*;
import com.techbank.account.exception.ApiError;
import com.techbank.account.cmd.service.AccountCommandDispatcher;
import com.techbank.account.exception.ErrorBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AccountCommandController.API_URL)
@RequiredArgsConstructor
@Slf4j
public class AccountCommandController {
    public static final String API_URL = "/api/v1/account/";

    private final AccountCommandDispatcher accountService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/replay", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void replayAccounts() {
        accountService.handle(new ReplayAccountEventsCommand(null));
    }
    
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/open", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> openAccount(@RequestBody OpenAccountCommand cmd) {
        cmd.setAggregateId(UUID.randomUUID());
        return ResponseEntity.ok(accountService.handle(cmd));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/{id}/close", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void closeAccount(@PathVariable("id") UUID id) {
        accountService.handle(new CloseAccountCommand(id));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/{id}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void withdraw(@RequestBody WithdrawFundsCommand cmd, @PathVariable("id") UUID id) {
        cmd.setAggregateId(id);
        accountService.handle(cmd);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/{id}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void deposit(@RequestBody DepositFundsCommand cmd, @PathVariable("id") UUID id) {
        cmd.setAggregateId(id);
        accountService.handle(cmd);
    }

    @ExceptionHandler(ApiError.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
    }
}
