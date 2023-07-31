package com.techbank.account.cmd.repository;

import com.techbank.account.base.events.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface EventStoreRepository extends MongoRepository<EventEntity, UUID> {
    List<EventEntity> findByAggregateId(String aggregateId);
}
