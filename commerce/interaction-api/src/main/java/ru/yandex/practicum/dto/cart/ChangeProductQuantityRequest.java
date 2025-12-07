package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProductQuantityRequest {
    @NotNull
    private String productId;
    @NotNull
    private Long newQuantity; // because int64}
}
