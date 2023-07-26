package com.techbank.account.base.messages;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public abstract class Message {
    private String id;
}
