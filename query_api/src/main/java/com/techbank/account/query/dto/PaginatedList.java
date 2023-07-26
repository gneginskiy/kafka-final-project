package com.techbank.account.query.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginatedList<DTO> {
    List<DTO> currentPage;
    int pageElements;
    int totalElements;
    int totalPages;
    int pageNumber;

    public static <E, DTO> PaginatedList<DTO> of(Page<E> page, Function<E, DTO> entityToDtoMapper) {
        return new PaginatedList<>(
                page.stream().map(entityToDtoMapper).toList(),
                page.getNumberOfElements(),
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber()
        );
    }
}
