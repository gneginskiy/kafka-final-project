package com.techbank.account.query.repository.util.getall.helpers;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.techbank.account.query.repository.util.getall.criteria.SearchCriteria;
import com.techbank.account.query.util.Futility;
import com.techbank.account.query.util.ReflectHelper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.*;
import java.util.stream.Stream;

import static com.techbank.account.query.util.Futility.readField;
import static com.techbank.account.query.util.ReflectHelper.getComparablePath;
import static org.apache.commons.lang3.StringUtils.*;

public class FilterHelper {
    private static final String FILTER_PAIRS_DELIMITER = ",";
    private static final String FIELD_VALUE_DELIMITER  = "_";
    private static final String FTS_PARAM_NAME         = "ft";
    private static final String FTS_COLUMN_NAME        = FTS_PARAM_NAME;
    private static final String IN_DELIMITER           = ":";

    @SneakyThrows
    public static <T> Predicate getQdslPredicate(SearchCriteria sc,
                                                 Class<T> clazz) {
        return Stream.of(
                getFtPredicate(sc.getFt()),
                getFilterByPredicate(clazz, sc.getFilterBy()),
                getCustomPredicate(clazz,sc)
        ).reduce(alwaysTrue(), BooleanExpression::and);
    }

    private static <T> BooleanExpression getCustomPredicate(Class<T> clazz, SearchCriteria sc) {
        return alwaysTrue();
    }

    private static BooleanExpression getFtPredicate(String ft) {
        if (StringUtils.isEmpty(ft)) return alwaysTrue();
        return ReflectHelper.getStringPath(FTS_COLUMN_NAME).containsIgnoreCase(ft);
    }

    private static <T> BooleanExpression getFilterByPredicate(Class<T> clazz, String filterBy) {
        //filter with exact match.
        if (isEmpty(filterBy)) {
            return alwaysTrue();
        }
        var result = alwaysTrue();
        T probe = getProbe(clazz, filterBy, "");
        for (String pair : filterBy.split(FILTER_PAIRS_DELIMITER)) {
            String fieldName = getFieldName(pair);
            String fieldValueStr = getFieldValue(pair);
            boolean isInCondition = fieldValueStr.contains(IN_DELIMITER);
            Object probeFieldValue = readField(probe, fieldName);
            var currentTerm = getBooleanExpression(fieldName, fieldValueStr, isInCondition, probeFieldValue,clazz);
            result=result.and(currentTerm);
        }
        return result;
    }

    private static <T>  BooleanExpression getBooleanExpression(String fieldName,
                                                          String fieldValueStr,
                                                          boolean isInCondition,
                                                          Object probeFieldValue,
                                                          Class<T> clazz) {
        if (!isInCondition) {
            return getQdslCondition(fieldName, probeFieldValue);
        } else {
            List<?> probeFieldValues = parseFieldValues(fieldValueStr,  FieldUtils.getField(clazz, fieldName,true).getType());
            return getQdslCondition(fieldName, probeFieldValues.toArray());
        }
    }

    private static List<?> parseFieldValues(String fieldValueStr, Class<?> fieldType) {
        return Arrays.stream(fieldValueStr.split(IN_DELIMITER))
                .map(val -> Futility.deepClone(val, fieldType))
                .toList();
    }

    @SuppressWarnings("unchecked")
    private static BooleanExpression getQdslCondition(String fieldName, Object... fieldValues) {
        var fieldValue = fieldValues.length == 0 ? null : fieldValues[0];
        if (!(fieldValues[0] instanceof Comparable)) {
            return alwaysTrue();
        }

        if (fieldValues.length == 1 && Boolean.FALSE.equals(fieldValues[0])) {
            return getComparablePath(fieldValue.getClass(), fieldName).isNull().or(
                    getComparablePath(fieldValue.getClass(), fieldName).eq(Boolean.FALSE));
        }

        if (fieldValues.length == 1) {
            return getComparablePath(fieldValue.getClass(), fieldName).eq(fieldValue);
        }
        return getComparablePath(fieldValue.getClass(), fieldName).in(fieldValues);
    }

    private static String getFirstFieldValue(String pair) {
        String value = substringAfter(pair, FIELD_VALUE_DELIMITER);
        return value.contains(IN_DELIMITER) ? substringAfter(value, IN_DELIMITER) : value;
    }

    private static String getFieldValue(String pair) {
        return substringAfter(pair, FIELD_VALUE_DELIMITER);
    }

    private static String getFieldName(String pair) {
        return substringBefore(pair, FIELD_VALUE_DELIMITER);
    }

    private static BooleanExpression alwaysTrue() {
        return Expressions.asBoolean(true).isTrue();
    }

    @SneakyThrows
    public static <T> T getProbe(Class<T> clazz, String filter, String ft) {
        var fieldValueMap = getFieldValueMap(filter, ft);
        return Futility.deepClone(fieldValueMap, clazz);
    }

    private static Map<String, String> getFieldValueMap(String filter, String ft) {
        var result = new HashMap<String, String>();
        if (isNotEmpty(ft)) result.put(FTS_PARAM_NAME, ft);
        if (isNotEmpty(filter)) {
            Arrays.stream(filter.split(FILTER_PAIRS_DELIMITER)).forEach(pair ->
                    result.put(getFieldName(pair), getFirstFieldValue(pair)));
        }
        return result;
    }

}
