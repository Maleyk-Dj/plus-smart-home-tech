package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public record OrderDto(
        String orderId,
        String shoppingCartId,
        @NotNull(message = "Отображение идентификатора товара на отобранное количество не может быть null")
        Map<String, Long> products,
        String paymentId,
        String deliveryId,
        StateOrder state,
        @DecimalMin(value = "0.0")
        @Digits(integer = 7, fraction = 3)
        Double deliveryWeight,
        @DecimalMin(value = "0.0")
        @Digits(integer = 12, fraction = 3)
        Double deliveryVolume,
        Boolean fragile,
        @DecimalMin(value = "0.0")
        @Digits(integer = 9, fraction = 2)
        BigDecimal totalPrice,
        @DecimalMin(value = "0.0")
        @Digits(integer = 9, fraction = 2)
        BigDecimal deliveryPrice,
        @DecimalMin(value = "0.0")
        @Digits(integer = 9, fraction = 2)
        BigDecimal productPrice
) {
}