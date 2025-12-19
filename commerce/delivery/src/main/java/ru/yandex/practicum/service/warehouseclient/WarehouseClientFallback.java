package ru.yandex.practicum.service.warehouseclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.exception.WarehouseServiceUnavailableException;

import java.util.Map;

@Slf4j
@Component
public class WarehouseClientFallback implements WarehouseClient {
    @Override
    public void addProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(ShoppingCartDto shoppingCartDto) {
        log.warn("Сервис склада не работает. Невозможно проверить наличие товара");
        throw new WarehouseServiceUnavailableException("Сервис склада не работает. Невозможно проверить наличие товара");
    }

    @Override
    public void addProductToWarehouse(ProductQuantityDto productQuantityDto) {
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return null;
    }

    @Override
    public BookedProductsDto prepareItemsForShipment(AssemblyProductsForOrderRequest assemblyProducts) {
        return null;
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        log.warn("Сервис склада не работает. Невозможно проверить наличие товара");
        throw new WarehouseServiceUnavailableException("Сервис склада не работает. Невозможно проверить наличие товара");
    }

    @Override
    public void acceptReturnItems(Map<String, Long> products) {
    }
}