package com.techbank.account.base.events;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("event_entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EventEntity {
    @Transient
    public static final String SEQUENCE_NAME = "event_id_seq";
    @Id
    private Long id;
    @Indexed(unique = false)
    private UUID aggregateId;
    private Long timestamp;
    private String aggregateType;
    private String eventType;
    private BaseEvent eventData;
}
