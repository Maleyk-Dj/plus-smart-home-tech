package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record AssemblyProductsForOrderRequest(
        @NotNull(message = "Перечень товаров не может быть null")
        Map<String, Long> products,
        @NotBlank(message = "Идентификатор заказа не может быть null")
        String orderId
) {
}