package com.techbank.account.dto.events;


import com.techbank.account.base.events.BaseEvent;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountFundsDepositedEvent extends BaseEvent {
    private int version;
    private BigDecimal amount;
}
