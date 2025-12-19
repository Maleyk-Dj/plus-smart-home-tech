package ru.yandex.practicum.dto.store;

import java.util.List;

public record PageResponse(
        List<ProductDto> content,
        List<SortOrder> sort
) {
}