package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetProductQuantityStateRequest {
    @NotNull
    private String productId;
    @NotNull
    private QuantityState quantityState;
}
