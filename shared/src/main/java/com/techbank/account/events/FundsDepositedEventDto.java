package com.techbank.account.events;


import com.techbank.account.base.events.BaseEventDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsDepositedEventDto extends BaseEventDto {
    private BigDecimal  amount;
}
