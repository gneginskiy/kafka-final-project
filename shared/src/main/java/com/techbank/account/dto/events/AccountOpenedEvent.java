package com.techbank.account.dto.events;


import com.techbank.account.dto.AccountType;
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
public class AccountOpenedEvent extends BaseEvent {
    private String      accountHolder;
    private AccountType accountType;
    private BigDecimal  openingBalance;
}
