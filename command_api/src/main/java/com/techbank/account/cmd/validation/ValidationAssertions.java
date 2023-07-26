package com.techbank.account.cmd.validation;

import com.techbank.account.cmd.exceptions.ApiError;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import static com.techbank.account.cmd.validation.AccountReflectUtil.readId;
import static java.util.stream.Collectors.toSet;

public class ValidationAssertions {
    public static void checkFieldNotEmpty(Object value, Object enclosing, String fieldname) {
        if (value == null || "".equals(value))
            throw ApiError.badRequest(enclosing.getClass(), readId(enclosing),
                    fieldname + " should not be empty");
    }

    public static void checkFieldEmpty(Object value, Object enclosing, String fieldname) {
        if (value != null || value instanceof String s && s.length()>0)
            throw ApiError.badRequest(enclosing.getClass(), readId(enclosing),
                    fieldname + " should be empty");
    }

    //greater or equal to zero
    public static void checkFieldGteZero(BigDecimal value, Object enclosing, String fieldname) {
        checkFieldNotEmpty(value,enclosing,fieldname);
        if (value.compareTo(BigDecimal.ZERO) <= 0)
            throw ApiError.badRequest(enclosing.getClass(), readId(enclosing),
                    fieldname + " should be >= 0. Actual value: " + value);
    }

    public static <OBJ, KEY> void checkUnique(Collection<OBJ> uniqueObjects,
                                              Function<OBJ, KEY> getObjKeyFunction,
                                              Object enclosing,
                                              String errMsg) {
        Set<KEY> uniqueKeys = uniqueObjects.stream().map(getObjKeyFunction).collect(toSet());
        if (uniqueKeys.size() != uniqueObjects.size()) {
            throw ApiError.badRequest(enclosing.getClass(), readId(enclosing), errMsg);
        }
    }

    public static void checkTrue(boolean val, Object enclosing, String msg) {
        if (!val) throw ApiError.badRequest(enclosing.getClass(), readId(enclosing), msg);
    }
}
