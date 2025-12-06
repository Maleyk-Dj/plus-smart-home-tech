package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    @Size(max = 255, message = "Идентификатор товара превысил 255 символов")
    private String productId;
    @NotBlank(message = "Отсутствует наименование товара")
    @Size(max = 255, message = "Наименование товара превысило 255 символов")
    private String productName;
    @NotBlank(message = "Отсутствует описание товара")
    @Size(max = 1000, message = "Описание превысило 1000 символов")
    private String description;
    private String imageSrc;
    @NotNull(message = "Отсутствует статус, перечисляющий состояние остатка как свойства товара")
    private QuantityState quantityState;
    @NotNull(message = "Отсутствует статус товара")
    private ProductState productState;
    private ProductCategory productCategory;
    @NotNull(message = "Отсутствует цена товара")
    @DecimalMin(value = "1.0", inclusive = false, message = "Цена товара не может быть меньше 1")
    @Positive(message = "Цена товара не может быть отрицательной")
    private Double price;
}