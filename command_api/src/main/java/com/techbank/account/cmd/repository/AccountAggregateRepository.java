package com.techbank.account.cmd.repository;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AccountAggregateRepository extends MongoRepository<AccountAggregate, UUID> {
}
