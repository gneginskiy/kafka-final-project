package com.techbank.account.query.repository.util.getall.helpers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public class SortHelper {
    private static final String SORT_PAIRS_DELIMITER = ",";
    private static final String SORT_FIELD_MODE_DELIMITER = "_";

    //?sortBy=clientId_ASC,status_DESC,
    public static <T> Sort getSortingStrategy(String sortBy) {
        return Arrays.stream(sortBy.split(SORT_PAIRS_DELIMITER))
                .filter(StringUtils::isNotEmpty)
                .map(SortHelper::getSortCondition)
                .reduce(Sort.unsorted(), Sort::and);
    }

    private static Sort getSortCondition(String pair) {
        var sortField = substringBeforeLast(pair, SORT_FIELD_MODE_DELIMITER);
        var mode      = substringAfterLast(pair, SORT_FIELD_MODE_DELIMITER);
        return "DESC".equalsIgnoreCase(mode) ?
                Sort.by(sortField).descending() : Sort.by(sortField).ascending();
    }
}
