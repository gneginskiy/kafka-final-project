package com.techbank.account.cmd.domain;

import com.techbank.cqrs.base.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateId(String aggregateId);
}
