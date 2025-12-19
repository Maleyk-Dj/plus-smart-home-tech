package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotBlank;

public record ShippedToDeliveryRequest(
        @NotBlank(message = "Идентификатор заказа в БД не может быть пустым или null")
        String orderId,
        @NotBlank(message = "Идентификатор доставки в БД не может быть пустым или null")
        String deliveryId
) {
}
