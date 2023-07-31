package com.techbank.account.cmd.service;

import com.techbank.account.base.events.EventEntity;
import com.techbank.account.cmd.entity.DatabaseSequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoSequenceGeneratorService {
    private final MongoOperations mongoOperations;

    public long generateEventIdSeq(){
        return generateSequence(EventEntity.SEQUENCE_NAME);
    }

    private long generateSequence(String seqName) {
        Query query = query(where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = options().returnNew(true).upsert(true);
        var counter = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1L;
    }
}
