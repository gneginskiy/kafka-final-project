package com.techbank.account.base.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "eventStore")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EventEntity {
    @Id
    private String id;
    private Long timestamp;
    private String aggregateId;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseEvent eventData;
}
