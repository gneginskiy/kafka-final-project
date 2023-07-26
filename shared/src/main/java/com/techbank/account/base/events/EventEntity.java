package com.techbank.account.base.events;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "EventEntity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EventEntity {
    @Id
    private String id;
    @Indexed(unique = false)
    private String aggregateId;
    private Long timestamp;
    private String aggregateType;
    private String eventType;
    private BaseEvent eventData;
}
