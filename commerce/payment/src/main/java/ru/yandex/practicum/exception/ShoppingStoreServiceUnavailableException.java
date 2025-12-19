package ru.yandex.practicum.exception;

public class ShoppingStoreServiceUnavailableException extends RuntimeException
{
    public ShoppingStoreServiceUnavailableException(String message) {
        super(message);
    }
}
