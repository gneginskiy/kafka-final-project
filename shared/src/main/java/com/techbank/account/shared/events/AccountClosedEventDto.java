package com.techbank.account.shared.events;

import com.techbank.cqrs.base.events.BaseEventDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AccountClosedEventDto extends BaseEventDto {
}
