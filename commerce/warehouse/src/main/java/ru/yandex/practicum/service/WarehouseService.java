package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.warehouse.*;

public interface WarehouseService {
    void addProduct(NewProductlnWarehouseRequest newProductInWarehouseRequest);

    BookedProductsDto sufficiencyCheck(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(ProductQuantityDto productQuantityDto);

    AddressDto getAddress();
}
