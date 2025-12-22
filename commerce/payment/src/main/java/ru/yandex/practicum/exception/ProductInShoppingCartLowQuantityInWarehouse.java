package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {
  private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
  private final String userMessage = "Ошибка, товар из корзины не находится в требуемом количестве на складе";

  public ProductInShoppingCartLowQuantityInWarehouse(String message) {
    super(message);
  }
}