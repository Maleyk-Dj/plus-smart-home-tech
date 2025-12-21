package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NoOrderFoundException extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
  private final String userMessage = "Нет искомого заказа";

  public NoOrderFoundException(String message) {
    super(message);
  }
}
