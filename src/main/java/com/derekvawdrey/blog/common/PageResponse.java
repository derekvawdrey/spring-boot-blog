package com.derekvawdrey.blog.common;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        boolean hasNext,
        boolean hasPrevious) {

    public static <E, T> PageResponse<T> from(Page<E> source, Function<E, T> mapper) {
        List<T> content = source.getContent().stream().map(mapper).toList();
        return new PageResponse<>(
                content,
                source.getNumber(),
                source.getSize(),
                source.getTotalElements(),
                source.getTotalPages(),
                source.isFirst(),
                source.isLast(),
                source.hasNext(),
                source.hasPrevious());
    }
}
