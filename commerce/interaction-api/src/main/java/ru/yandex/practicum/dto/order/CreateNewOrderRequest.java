package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;

public record CreateNewOrderRequest(
        @NotNull(message = "Корзина товаров в онлайн магазине не может быть null")
        ShoppingCartDto shoppingCart,
        @NotNull(message = "Представление адреса в системе не может быть null")
        AddressDto deliveryAddress
) {
}