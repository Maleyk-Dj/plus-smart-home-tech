package ru.yandex.practicum.exception;

/**
 * Простое семантическое исключение. Не содержит HttpStatus — mapping делаем в обработчике.
 */
public class NoSpecifiedProductInWarehouseException extends RuntimeException {
    public NoSpecifiedProductInWarehouseException(String message) {
        super(message);
    }

    public NoSpecifiedProductInWarehouseException(String message, Throwable cause) {
        super(message, cause);
    }
}
