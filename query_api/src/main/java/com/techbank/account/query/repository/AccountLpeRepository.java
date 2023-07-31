package com.techbank.account.query.repository;

import com.techbank.account.query.entity.LpeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLpeRepository extends JpaRepository<LpeEntity, Long>{
}
