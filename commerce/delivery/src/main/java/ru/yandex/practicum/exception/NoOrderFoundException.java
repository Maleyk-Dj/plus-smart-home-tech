package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NoOrderFoundException extends RuntimeException {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    String userMessage = "Нет искомого заказа";

    public NoOrderFoundException(String message) {
        super(message);
    }
}