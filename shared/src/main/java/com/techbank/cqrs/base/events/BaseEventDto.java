package com.techbank.cqrs.base.events;

import com.techbank.cqrs.base.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEventDto extends Message {
    private int version;
}
