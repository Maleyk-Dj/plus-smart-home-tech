package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NoDeliveryFoundException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String userMessage = "Нет искомой доставки";

    public NoDeliveryFoundException(String message) {
        super(message);
    }
}
