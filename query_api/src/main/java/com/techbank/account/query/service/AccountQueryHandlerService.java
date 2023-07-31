package com.techbank.account.query.service;

import com.techbank.account.exception.ApiError;
import com.techbank.account.query.controller.GetAllRqDetails;
import com.techbank.account.query.dto.AccountDto;
import com.techbank.account.query.dto.PaginatedList;
import com.techbank.account.query.repository.AccountRepository;
import com.techbank.account.query.util.Futility;
import com.techbank.account.query.converter.UnifiedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountQueryHandlerService {
    private final AccountRepository accountRepository;
    private final UnifiedMapper mapper;

    public AccountDto get(String id) {
        return accountRepository.findById(id)
                .map(a->Futility.deepClone(a,AccountDto.class))
                .orElseThrow(() -> ApiError.notFound(AccountDto.class, id, null));
    }

    public PaginatedList<AccountDto> getAll(GetAllRqDetails rq) {
        var page = accountRepository.findAll(rq.predicate(), rq.pageable());
        return PaginatedList.of(page, mapper::toDto);
    }
}
