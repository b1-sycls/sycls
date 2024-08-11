package com.b1.common;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponseDto<T> {

    private final Integer currentPage;
    private final Integer totalPage;
    private final Long totalElements;
    private final Boolean hasNextPage;
    private final List<T> data;

    public static <T> PageResponseDto<T> of(final Page<T> page) {
        return PageResponseDto.<T>builder()
                .currentPage(page.getNumber() + 1)
                .totalPage(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .hasNextPage(page.hasNext())
                .data(page.getContent())
                .build();
    }
}