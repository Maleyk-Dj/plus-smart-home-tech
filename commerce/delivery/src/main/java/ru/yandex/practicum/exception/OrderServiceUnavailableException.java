package ru.yandex.practicum.exception;

public class OrderServiceUnavailableException extends RuntimeException {
  public OrderServiceUnavailableException(String message) {
    super(message);
  }
}