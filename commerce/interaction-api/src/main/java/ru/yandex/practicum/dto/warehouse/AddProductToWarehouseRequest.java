package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToWarehouseRequest {

    @NotNull(message = "Идентификатор товара обязателен")
    private String productId;

    @NotNull(message = "Количество обязательно")
    @Min(value = 1, message = "Количество должно быть не менее 1")
    private Integer quantity;
}
