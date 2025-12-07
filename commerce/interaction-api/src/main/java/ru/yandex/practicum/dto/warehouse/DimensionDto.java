package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDto {

    @NotNull(message = "Ширина обязательна")
    @DecimalMin(value = "1.0", message = "Ширина должна быть не менее 1")
    private Double width; // number($double), minimum: 1

    @NotNull(message = "Высота обязательна")
    @DecimalMin(value = "1.0", message = "Высота должна быть не менее 1")
    private Double height; // number($double), minimum: 1, example: 1

    @NotNull(message = "Глубина обязательна")
    @DecimalMin(value = "1.0", message = "Глубина должна быть не менее 1")
    private Double depth; // number($double), minimum: 1, example: 1
}

