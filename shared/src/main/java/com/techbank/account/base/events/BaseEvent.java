package com.techbank.account.base.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public abstract class BaseEvent {
    private UUID aggregateId; //entity applied to
    private UUID id;          //event id with timestamp
    private Long timestamp;

    public <T extends BaseEvent> T setId(UUID id) {
        this.id = id;
        return (T) this;
    }

    public <T extends BaseEvent> T setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
        return (T) this;
    }

    public <T extends BaseEvent> T setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return (T) this;
    }
}
