package com.techbank.account.query.entity;

import com.techbank.account.base.events.BaseEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    private Long lpeId;

    public static LpeEntity of(BaseEvent evt) {
        return new LpeEntity(0L, evt.getId());
    }
}
