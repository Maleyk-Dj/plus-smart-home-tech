package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.*;

import java.util.Map;

public record ProductReturnRequest(
        String orderId,
        @NotNull(message = "Отображение идентификатора товара на отобранное количество не может быть null")
        Map<String, Long> products
) {
}
