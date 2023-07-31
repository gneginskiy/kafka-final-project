package com.techbank.account.query.entity;

import com.techbank.account.base.events.BaseEvent;
import com.techbank.account.dto.AccountType;
import com.techbank.account.dto.events.AccountOpenedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LpeEntity {
    public static final LpeEntity DEFAULT = new LpeEntity(0L,0L);
    @Id
    private Long id;
    private Long ts;

    public static LpeEntity of(BaseEvent evt) {
        return new LpeEntity(0L, evt.getTimestamp());
    }
}
