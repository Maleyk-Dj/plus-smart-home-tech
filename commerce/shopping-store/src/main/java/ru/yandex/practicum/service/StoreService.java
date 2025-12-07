package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.store.*;


public interface StoreService {

    PageResponse getAllByType(ProductCategory category, Pageable customPageable);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(UpdateProductDto productDto);
    String removeProduct(String productId);
    String setQuantityState(SetProductQuantityStateRequest stateRequest);
    ProductDto getProduct(String productId);
}