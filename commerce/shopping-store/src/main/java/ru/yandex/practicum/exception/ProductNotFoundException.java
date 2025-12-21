package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductNotFoundException extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
  private final String userMessage = "Ошибка, товар по идентификатору в БД не найден";

  public ProductNotFoundException(String message) {
    super(message);
  }
}