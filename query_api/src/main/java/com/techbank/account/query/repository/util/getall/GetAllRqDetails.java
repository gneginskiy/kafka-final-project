package com.techbank.account.query.repository.util.getall;

import com.querydsl.core.types.Predicate;
import com.techbank.account.query.repository.util.getall.criteria.SearchCriteria;
import com.techbank.account.query.repository.util.getall.helpers.FilterHelper;
import com.techbank.account.query.repository.util.getall.helpers.SortHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Accessors(fluent = true)
public class GetAllRqDetails {
    Pageable pageable;
    Predicate predicate;

    public static <T> GetAllRqDetails of(Class<T> clazz, SearchCriteria criteria) {
        PageRequest pageRequest = buildPageRequest(criteria);
        Predicate   predicate   = buildPredicate(clazz,criteria);
        return new GetAllRqDetails(pageRequest, predicate);
    }

    @SneakyThrows
    private static <T> Predicate buildPredicate(Class<T> clazz, SearchCriteria sc) {
        return FilterHelper.getQdslPredicate(sc, clazz);
    }

    private static PageRequest buildPageRequest(SearchCriteria criteria) {
        Sort sortingStrategy = SortHelper.getSortingStrategy(criteria.getSortBy());
        return PageRequest.of(criteria.getPageNum(), criteria.getPageSize(), sortingStrategy);
    }
}
