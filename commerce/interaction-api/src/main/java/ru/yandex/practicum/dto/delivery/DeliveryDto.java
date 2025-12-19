package ru.yandex.practicum.dto.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.dto.warehouse.AddressDto;


public record DeliveryDto(
        String deliveryId,
        @NotNull(message = "Представление адреса в системе не может быть null")
        AddressDto fromAddress,
        @NotNull(message = "Представление адреса в системе не может быть null")
        AddressDto toAddress,
        @NotBlank(message = "Идентификатор заказа не может быть null")
        String orderId,
        @NotNull(message = "Статус заказа не может быть null")
        DeliveryState deliveryState
) {
}