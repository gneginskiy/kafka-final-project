package com.techbank.account.query.controller;

import com.techbank.account.exception.ApiError;
import com.techbank.account.exception.ErrorBody;
import com.techbank.account.query.dto.AccountDto;
import com.techbank.account.query.dto.PaginatedList;
import com.techbank.account.query.entity.AccountEntity;
import com.techbank.account.query.repository.util.getall.criteria.SearchCriteria;
import com.techbank.account.query.service.AccountQueryHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(AccountQueryController.API_URL)
@RequiredArgsConstructor
@Slf4j
public class AccountQueryController {
    public static final String API_URL = "/api/v1/";

    private final AccountQueryHandlerService accountService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PaginatedList<AccountDto> getAll(SearchCriteria searchCriteria) {
        return accountService.getAll(GetAllRqDetails.of(AccountEntity.class, searchCriteria));
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public AccountDto get(@PathVariable UUID id) {
        return accountService.get(id);
    }

    @ExceptionHandler(ApiError.class)
    protected ResponseEntity<?> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorBody(e.getMessage()));
    }
}
