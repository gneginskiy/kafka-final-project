package com.techbank.account.query.repository;

import com.techbank.account.query.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, String>,
                                           QuerydslPredicateExecutor<AccountEntity> {
}
