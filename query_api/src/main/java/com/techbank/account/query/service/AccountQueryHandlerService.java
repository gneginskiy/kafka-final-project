package com.techbank.account.query.service;

import com.techbank.account.exception.ApiError;
import com.techbank.account.query.controller.GetAllRqDetails;
import com.techbank.account.query.dto.AccountDto;
import com.techbank.account.query.dto.PaginatedList;
import com.techbank.account.query.repository.AccountRepository;
import com.techbank.account.query.validation.AccountQueryValidator;
import converter.UnifiedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountQueryHandlerService {
    private final AccountQueryValidator validator;
    private final AccountRepository accountRepository;
    private final UnifiedMapper mapper;

    public AccountDto get(UUID id) {
        validator.validateGet(id);
        return accountRepository.findById(id.toString())
                .map(mapper::toDto)
                .orElseThrow(() -> ApiError.notFound(AccountDto.class, id, null));
    }

    public PaginatedList<AccountDto> getAll(GetAllRqDetails rq) {
        var page = accountRepository.findAll(rq.predicate(), rq.pageable());
        return PaginatedList.of(page, mapper::toDto);
    }
}
