package com.techbank.account.dto.events;


import com.techbank.account.base.events.BaseEvent;
import lombok.*;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountClosedEvent implements BaseEvent {
    private String id;
    private int version;
    private Long timestamp;
}
