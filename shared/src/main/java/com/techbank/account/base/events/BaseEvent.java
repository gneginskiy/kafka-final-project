package com.techbank.account.base.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class BaseEvent {
    private int version;
    private String aggregateId; //entity applied to
    private Long id;          //event id with timestamp
    private Long timestamp;

    public <T extends BaseEvent> T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public <T extends BaseEvent> T setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
        return (T) this;
    }

    public <T extends BaseEvent> T setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return (T) this;
    }
}
