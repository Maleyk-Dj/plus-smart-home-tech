package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedProductsDto {

    @NotNull(message = "Delivery weight is required")
    @PositiveOrZero(message = "Delivery weight must be positive or zero")
    private Double deliveryWeight;

    @NotNull(message = "Delivery volume is required")
    @PositiveOrZero(message = "Delivery volume must be positive or zero")
    private Double deliveryVolume;

    @NotNull(message = "Fragile flag is required")
    private Boolean fragile;
}
