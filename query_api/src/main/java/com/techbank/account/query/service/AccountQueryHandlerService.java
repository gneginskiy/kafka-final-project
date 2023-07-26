package com.techbank.account.query.service;

import com.techbank.account.query.controller.GetAllRqDetails;
import com.techbank.account.query.dto.AccountDto;
import com.techbank.account.query.dto.PaginatedList;
import com.techbank.account.query.repository.AccountRepository;
import com.techbank.account.query.validation.AccountQueryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountQueryHandlerService {
    private final AccountQueryValidator validator;
    private final AccountRepository accountRepository;

    public AccountDto get(UUID id) {
        validator.validateGet(id);
        accountRepository.findById(id.toString());
        return null;
    }

    public PaginatedList<AccountDto> getAll(GetAllRqDetails of) {
        return null;
    }
}
