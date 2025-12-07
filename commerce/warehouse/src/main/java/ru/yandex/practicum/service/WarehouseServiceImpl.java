package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.entity.Product;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseServiceImpl implements WarehouseService{
    private final WarehouseRepository warehouseRepository;
    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, ADDRESSES.length)];

    @Override
    @Transactional
    public void addProduct(NewProductlnWarehouseRequest newProductInWarehouseRequest) {
        if (warehouseRepository.existsById(newProductInWarehouseRequest.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Ошибка, товар с таким описанием уже " +
                    "зарегистрирован на складе");
        }

        Product product = Mapper.mapToProduct(newProductInWarehouseRequest);
        warehouseRepository.save(product);
    }

    @Override
    public BookedProductsDto sufficiencyCheck(ShoppingCartDto shoppingCartDto) {
        Map<String, Long> productsInCars = shoppingCartDto.getProducts();
        List<Product> productList = warehouseRepository.findAllById(productsInCars.keySet());

        Set<String> productIds = productList.stream().map(Product::getProductId).collect(Collectors.toSet());
        if (!productIds.containsAll(shoppingCartDto.getProducts().keySet())) {
            throw new NoSpecifiedProductInWarehouseException("На складе нет информации о некоторых товарах");
        }

        double deliveryVolume = 0;
        double deliveryWeight = 0;
        boolean fragile = false;
        List<String> productInShoppingCartLowQuantityInWarehouse = new ArrayList<>();
        for (Product product : productList) {
            long quantity = productsInCars.get(product.getProductId());
            if (product.getQuantity() < quantity) {
                productInShoppingCartLowQuantityInWarehouse.add(product.getProductId());
            }

            deliveryVolume += (product.getWidth() * product.getHeight() * product.getDepth()) * quantity;
            deliveryWeight += product.getWeight() * quantity;
            fragile = product.isFragile() ? true : fragile;
        }

        if (!productInShoppingCartLowQuantityInWarehouse.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse
                    (String.format("Ошибка, эти товары из корзины не находится в требуемом количестве на складе: %s",
                            productInShoppingCartLowQuantityInWarehouse));
        }

        return Mapper.mapToBookedProductsDto(deliveryVolume, deliveryWeight, fragile);
    }

    @Override
    @Transactional
    public void addProductToWarehouse(ProductQuantityDto productQuantityDto) {
        Product product = warehouseRepository.findById(productQuantityDto.getProductId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException(
                        String.format("На складе нет информации о товаре с id = %s", productQuantityDto.getProductId())));
        long quantity = product.getQuantity();
        product.setQuantity(quantity + productQuantityDto.getQuantity());
    }

    @Override
    public AddressDto getAddress() {
        return new AddressDto(CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS);
    }
}
