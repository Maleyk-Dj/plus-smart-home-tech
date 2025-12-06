package ru.yandex.practicum.dto.store;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProductDto {
    @Size(max = 255, message = "Идентификатор товара превысил 255 символов")
    private String productId;
    @Size(max = 255, message = "Наименование товара превысило 255 символов")
    private String productName;
    @Size(max = 1000, message = "Описание превысило 1000 символов")
    private String description;
    private String imageSrc;
    private QuantityState quantityState;
    private ProductState productState;
    private ProductCategory productCategory;
    @DecimalMin(value = "1.0", message = "Цена товара не может быть меньше 1")
    @Positive(message = "Цена товара не может быть отрицательной")
    private Double price;
}
