package com.techbank.account.dto.events;


import com.techbank.account.base.events.BaseEvent;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountFundsWithdrawnEvent implements BaseEvent {
    private String id;
    private int version;
    private Long timestamp;

    private BigDecimal amount;
}
