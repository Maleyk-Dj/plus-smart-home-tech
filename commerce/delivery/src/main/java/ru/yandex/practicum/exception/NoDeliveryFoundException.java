package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NoDeliveryFoundException extends RuntimeException {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    String userMessage = "Нет искомой доставки";

    public NoDeliveryFoundException(String message) {
        super(message);
    }
}
