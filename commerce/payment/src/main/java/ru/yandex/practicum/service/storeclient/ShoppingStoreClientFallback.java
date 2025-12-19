package ru.yandex.practicum.service.storeclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.store.*;
import ru.yandex.practicum.exception.ShoppingStoreServiceUnavailableException;

@Component
@Slf4j
public class ShoppingStoreClientFallback implements ShoppingStoreClient {
    @Override
    public PageResponse getAllByType(ProductCategory category, Pageable pageable) {
        return null;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(UpdateProductDto productDto) {
        return null;
    }

    @Override
    public String removeProduct(String productId) {
        return "";
    }

    @Override
    public String setQuantityState(SetProductQuantityStateRequest stateRequest) {
        return "";
    }

    @Override
    public ProductDto getProduct(String productId) {
        log.warn("Сервис витрины онлайн магазина не работает. Невозможно проверить стоимость товара");
        throw new ShoppingStoreServiceUnavailableException("Сервис витрины онлайн магазина не работает. Невозможно проверить стоимость товара");
    }
}