package com.techbank.account.cmd.validation;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.FieldUtils;

@UtilityClass
public class AccountReflectUtil {

    @SneakyThrows
    public static <ID> ID readId(Object obj) {
        return (ID) FieldUtils.readField(obj, "id", true);
    }

    @SneakyThrows
    public static <ID> ID readAmount(Object obj) {
        return (ID) FieldUtils.readField(obj, "amount", true);
    }

}
