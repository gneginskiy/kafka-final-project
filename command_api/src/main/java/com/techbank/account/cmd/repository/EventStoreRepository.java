package com.techbank.account.cmd.repository;

import com.techbank.cqrs.base.events.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventStoreRepository extends MongoRepository<EventEntity, String> {
    List<EventEntity> findByAggregateId(String aggregateId);
}
