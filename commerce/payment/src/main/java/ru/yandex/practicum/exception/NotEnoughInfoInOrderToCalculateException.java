package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughInfoInOrderToCalculateException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final String userMessage = "Недостаточно информации в заказе для расчёта";

    public NotEnoughInfoInOrderToCalculateException(String message) {
        super(message);
    }
}
