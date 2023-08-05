package com.techbank.account.query.converter;

import com.techbank.account.query.dto.AccountDto;
import com.techbank.account.query.entity.AccountEntity;
import com.techbank.account.query.util.Futility;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static com.techbank.account.query.util.Futility.toFtsIndex;

@Mapper(componentModel = "spring")
public abstract class UnifiedMapper {
    //account mappers
    public abstract AccountDto toDto(AccountEntity entity);

    @AfterMapping //to hibernate layer.
    public void setFtsIndexValue(@MappingTarget Object entity) {
        Futility.tryToSet(entity, "ft", () -> null);
        Futility.tryToSet(entity, "ft", () -> toFtsIndex(entity));
    }
}
