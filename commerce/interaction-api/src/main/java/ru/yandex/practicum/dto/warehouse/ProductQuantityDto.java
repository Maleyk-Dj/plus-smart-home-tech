package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDto {
    @NotBlank(message = "Идентификатор товара в БД не может быть пустым или null")
    private String productId;

    @NotNull(message = "Количество товара не может быть null")
    private Long quantity;
}
