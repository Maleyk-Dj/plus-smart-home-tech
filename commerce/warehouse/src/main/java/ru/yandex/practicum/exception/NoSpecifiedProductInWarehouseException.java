package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoSpecifiedProductInWarehouseException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final String userMessage = "Нет информации о товаре на складе";

    public NoSpecifiedProductInWarehouseException(String message) {
        super(message);
    }
}
