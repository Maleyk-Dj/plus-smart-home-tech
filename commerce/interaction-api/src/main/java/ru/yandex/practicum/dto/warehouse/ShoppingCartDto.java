package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    @NotBlank(message = "Идентификатор корзины в БД не может быть пустым или null")
    private String shoppingCartId;

    @NotNull(message = "Отображение идентификатора товара на отобранное количество не может быть null")
    private Map<String, Long> products;

}
