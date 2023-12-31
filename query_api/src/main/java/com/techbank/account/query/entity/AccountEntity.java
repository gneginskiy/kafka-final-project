package com.techbank.account.query.entity;

import com.techbank.account.dto.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AccountEntity {
    @Id
    private String id;
    private String accountHolder;
    private Long createdAt;
    private AccountType accountType;
    private BigDecimal balance;
    private boolean active;

    @Column(columnDefinition = "TEXT")
    private String ft;
}
