package com.techbank.account.cmd.controller;

import com.techbank.account.base.messages.Message;
import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.exceptions.ApiError;
import com.techbank.account.cmd.service.AccountCommandHandlerService;
import com.techbank.account.dto.events.AccountClosedEvent;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AccountCommandController.API_URL)
@RequiredArgsConstructor
@Slf4j
public class AccountCommandController {
    public static final String API_URL = "/api/v1/";

    private final AccountCommandHandlerService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/open", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> openAccount(@RequestBody OpenAccountCommand cmd) {
        return ResponseEntity.ok(accountService.handle(cmd));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/close", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void closeAccount(@PathVariable("id") String id) {
        accountService.handle(new CloseAccountCommand(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void withdraw(@RequestBody WithdrawFundsCommand cmd, @PathVariable("id") String id) {
        cmd.setId(id);
        accountService.handle(cmd);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void deposit(@RequestBody DepositFundsCommand cmd, @PathVariable("id") String id) {
        cmd.setId(id);
        accountService.handle(cmd);
    }

    @ExceptionHandler(ApiError.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
    }

    @Value
    static class ErrorBody {
        String error;
    }
}
