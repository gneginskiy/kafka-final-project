package com.techbank.account.cmd.repository;

import com.techbank.account.cmd.aggregates.AccountAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountAggregateRepository extends MongoRepository<AccountAggregate, String> {
}
