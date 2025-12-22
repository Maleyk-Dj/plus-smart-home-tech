package ru.yandex.practicum.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.contract.warehouse.Warehouse;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.util.Loggable;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController implements Warehouse {
    private final WarehouseService service;

    @Override
    @PutMapping
    @Loggable
    public void addProduct(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequest) {
        service.addProduct(newProductInWarehouseRequest);
    }

    @Override
    @PostMapping("/check")
    @Loggable
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        return service.assemblyProductForOrderFromShoppingCart(shoppingCartDto);
    }

    @Override
    @PostMapping("/add")
    @Loggable
    public void addProductToWarehouse(@RequestBody @Valid ProductQuantityDto productQuantityDto) {
        service.addProductToWarehouse(productQuantityDto);
    }

    @Override
    @GetMapping("/address")
    @Loggable
    public AddressDto getWarehouseAddress() {
        return service.getWarehouseAddress();
    }

    @Override
    @PostMapping("/assembly")
    @Loggable
    public BookedProductsDto prepareItemsForShipment(@RequestBody AssemblyProductsForOrderRequest assemblyProducts) {
        return service.prepareItemsForShipment(assemblyProducts);
    }

    @Override
    @PostMapping("/shipped")
    @Loggable
    public void shippedToDelivery(@RequestBody ShippedToDeliveryRequest shippedToDeliveryRequest) {
        service.shippedToDelivery(shippedToDeliveryRequest);
    }

    @Override
    @PostMapping("/return")
    @Loggable
    public void acceptReturnItems(@RequestBody Map<String, Long> products) {
        service.acceptReturnItems(products);
    }
}