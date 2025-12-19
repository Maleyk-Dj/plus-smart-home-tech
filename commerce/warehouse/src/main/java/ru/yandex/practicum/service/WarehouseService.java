package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;

import java.util.Map;


public interface WarehouseService {
    void addProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    BookedProductsDto assemblyProductForOrderFromShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(ProductQuantityDto productQuantityDto);

    AddressDto getWarehouseAddress();

    BookedProductsDto prepareItemsForShipment(AssemblyProductsForOrderRequest assemblyProducts);

    void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest);

    void acceptReturnItems(Map<String, Long> products);
}