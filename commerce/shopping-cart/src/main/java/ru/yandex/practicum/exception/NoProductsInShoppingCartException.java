package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

public class NoProductsInShoppingCartException extends RuntimeException {
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String userMessage = "Нет искомых товаров в корзине";

    public NoProductsInShoppingCartException(String message) {
        super(message);
    }
}
