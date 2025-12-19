package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        @Size(max = 255, message = "Идентификатор товара превысил 255 символов")
        String productId,
        @NotBlank(message = "Отсутствует наименование товара")
        @Size(max = 255, message = "Наименование товара превысило 255 символов")
        String productName,
        @NotBlank(message = "Отсутствует описание товара")
        @Size(max = 1000, message = "Описание превысило 1000 символов")
        String description,
        String imageSrc,
        @NotNull(message = "Отсутствует статус, перечисляющий состояние остатка как свойства товара")
        QuantityState quantityState,
        @NotNull(message = "Отсутствует статус товара")
        ProductState productState,
        ProductCategory productCategory,
        @NotNull(message = "Отсутствует цена товара")
        @DecimalMin(value = "1.0", message = "Цена товара не может быть меньше 1")
        @Positive(message = "Цена товара не может быть отрицательной")
        BigDecimal price) {
}