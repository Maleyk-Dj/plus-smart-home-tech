package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {
    @PositiveOrZero(message = "Страница не может быть отрицательной")
    private Integer page; // Текущая страница (0-based)

    @Positive(message = "Размер страницы не может быть отрицательным или равняться нулю")
    private Integer size; // Количество элементов на странице

    private String[] sort;
}
