package ru.yandex.practicum.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;

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

    @ExceptionHandler(NoSpecifiedProductInWarehouseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public NoSpecifiedProductInWarehouseException handleNoSpecifiedProductInWarehouseException(NoSpecifiedProductInWarehouseException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new NoSpecifiedProductInWarehouseException(e.getMessage());
    }

    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProductInShoppingCartLowQuantityInWarehouse handleProductInShoppingCartLowQuantityInWarehouse(
            ProductInShoppingCartLowQuantityInWarehouse e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ProductInShoppingCartLowQuantityInWarehouse(e.getMessage());
    }

    @ExceptionHandler(SpecifiedProductAlreadyInWarehouseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SpecifiedProductAlreadyInWarehouseException handleSpecifiedProductAlreadyInWarehouseException(
            SpecifiedProductAlreadyInWarehouseException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new SpecifiedProductAlreadyInWarehouseException(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception e) {
        log.warn("Исключение: {} Сообщение: {} ", e.getClass().getSimpleName(), e.getMessage());
        return new ApiError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
    }
}

