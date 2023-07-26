package com.techbank.account.cmd.controller;

import com.techbank.account.cmd.commands.CloseAccountCommand;
import com.techbank.account.cmd.commands.DepositFundsCommand;
import com.techbank.account.cmd.commands.OpenAccountCommand;
import com.techbank.account.cmd.commands.WithdrawFundsCommand;
import com.techbank.account.cmd.exceptions.ApiError;
import com.techbank.account.cmd.service.AccountCommandHandlerService;
import lombok.RequiredArgsConstructor;
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
    public static final String API_URL = "/api/v1/controller/";

    private final AccountCommandHandlerService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/open/",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody OpenAccountCommand cmd, @PathVariable String id) {
        cmd.setId(id);
        return ResponseEntity.ok(accountService.handle(cmd));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/close", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void create(@RequestBody CloseAccountCommand cmd, @PathVariable String id) {
        cmd.setId(id);
        accountService.handle(cmd);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/withdraw/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void create(@RequestBody WithdrawFundsCommand cmd, @PathVariable String id) {
        cmd.setId(id);
        accountService.handle(cmd);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void create(@RequestBody DepositFundsCommand cmd, @PathVariable String id) {
        cmd.setId(id);
        accountService.handle(cmd);
    }

    @ExceptionHandler(ApiError.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e);
    }
}
