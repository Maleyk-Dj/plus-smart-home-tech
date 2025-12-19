package ru.yandex.practicum.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.exception.*;

import java.time.LocalDateTime;


@ControllerAdvice
@Slf4j
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProductInShoppingCartLowQuantityInWarehouse handleProductInShoppingCartLowQuantityInWarehouse(
            ProductInShoppingCartLowQuantityInWarehouse e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ProductInShoppingCartLowQuantityInWarehouse(e.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidRequestException(InvalidRequestException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleMethodHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(NoProductsInShoppingCartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public NoProductsInShoppingCartException handleNoProductsInShoppingCartException(NoProductsInShoppingCartException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new NoProductsInShoppingCartException(e.getMessage());
    }

    @ExceptionHandler(NotAuthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public NotAuthorizedUserException handleNotAuthorizedUserException(NotAuthorizedUserException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new NotAuthorizedUserException(e.getMessage());
    }

    @ExceptionHandler(NoOrderFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public NoOrderFoundException handleNoOrderFoundException(NoOrderFoundException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new NoOrderFoundException(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
    }

    @ExceptionHandler(WarehouseServiceUnavailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ApiError handleWarehouseServiceUnavailableException(WarehouseServiceUnavailableException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
    }
}