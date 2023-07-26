package com.techbank.account.cmd.aggregates;


import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collation = "AccountAggregate")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountAggregate {
    @Id
    private String id;
    private boolean active;
    private BigDecimal balance;
    @Version
    private int version;
}
