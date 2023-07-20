package com.techbank.account.shared.events;


import com.techbank.account.shared.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String      accountHolder;
    private AccountType accountType;
    private BigDecimal  openingBalance;
    private Long createdAt;

}
