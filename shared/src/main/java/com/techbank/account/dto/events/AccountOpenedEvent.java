package com.techbank.account.dto.events;


import com.techbank.account.dto.AccountType;
import com.techbank.account.base.events.BaseEvent;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountOpenedEvent implements BaseEvent {
    private String id;
    private int version;
    private Long createdAt;

    private String      accountHolder;
    private AccountType accountType;
    private BigDecimal  openingBalance;

}
