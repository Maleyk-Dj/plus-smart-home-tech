package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProductlnWarehouseRequest {

    @NotNull(message = "Идентификатор товара обязателен")
    private String productId; // string($uuid), обязательное (*)

    private Boolean fragile; // boolean, необязательное (нет звездочки)

    @Valid // Для каскадной валидации вложенного объекта
    @NotNull(message = "Размеры товара обязательны")
    private DimensionDto dimension; // DimensionDto, обязательное (*)

    @NotNull(message = "Вес товара обязателен")
    @DecimalMin(value = "1.0", message = "Вес должен быть не менее 1")
    private Double weight; // number($double), minimum: 1, обязательное (*)
}
