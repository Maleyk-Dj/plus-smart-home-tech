package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.*;
import ru.yandex.practicum.entity.OrderBooking;
import ru.yandex.practicum.entity.Product;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.repository.OrderBookingsRepository;
import ru.yandex.practicum.repository.ProductRepository;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final ProductRepository productRepository;
    private final OrderBookingsRepository orderBookingsRepository;
    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, ADDRESSES.length)];

    @Override
    @Transactional
    public void addProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        if (productRepository.existsById(newProductInWarehouseRequest.productId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Ошибка, товар с таким описанием уже " +
                    "зарегистрирован на складе");
        }

        Product product = Mapper.mapToProduct(newProductInWarehouseRequest);
        productRepository.save(product);
    }

    @Override
    public BookedProductsDto assemblyProductForOrderFromShoppingCart(ShoppingCartDto shoppingCartDto) {
        Map<String, Long> productsInCart = shoppingCartDto.products();
        List<Product> productList = productRepository.findAllById(productsInCart.keySet());

        Set<String> productIds = productList.stream().map(Product::getProductId).collect(Collectors.toSet());
        if (!productIds.containsAll(shoppingCartDto.products().keySet())) {
            throw new NoSpecifiedProductInWarehouseException("На складе нет информации о некоторых товарах");
        }

        double deliveryVolume = 0;
        double deliveryWeight = 0;
        boolean fragile = false;
        List<String> productInShoppingCartLowQuantityInWarehouse = new ArrayList<>();
        for (Product product : productList) {
            long quantity = productsInCart.get(product.getProductId());
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

        return Mapper.mapToBookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }

    @Override
    @Transactional
    public void addProductToWarehouse(ProductQuantityDto productQuantityDto) {
        Product product = productRepository.findById(productQuantityDto.productId())
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException(
                        String.format("На складе нет информации о товаре с id = %s", productQuantityDto.productId())));
        long quantity = product.getQuantity();
        product.setQuantity(quantity + productQuantityDto.quantity());
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return new AddressDto(CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS);
    }

    @Override
    @Transactional
    public BookedProductsDto prepareItemsForShipment(AssemblyProductsForOrderRequest assemblyProducts) {
        Map<String, Long> productsInOrder = assemblyProducts.products();
        List<Product> productList = productRepository.findAllById(productsInOrder.keySet());

        Set<String> productIds = productList.stream().map(Product::getProductId).collect(Collectors.toSet());
        if (!productIds.containsAll(assemblyProducts.products().keySet())) {
            throw new NoSpecifiedProductInWarehouseException("На складе нет информации о некоторых товарах");
        }

        double deliveryVolume = 0;
        double deliveryWeight = 0;
        boolean fragile = false;
        List<String> productInShoppingCartLowQuantityInWarehouse = new ArrayList<>();
        List<OrderBooking> orderBookings = new ArrayList<>();
        for (Product product : productList) {
            long quantity = productsInOrder.get(product.getProductId());
            if (product.getQuantity() < quantity) {
                productInShoppingCartLowQuantityInWarehouse.add(product.getProductId());
            }
            product.setQuantity(product.getQuantity() - quantity);
            orderBookings.add(OrderBooking.builder()
                    .orderId(assemblyProducts.orderId())
                    .productId(product.getProductId())
                    .quantity(quantity)
                    .build());
            deliveryVolume += (product.getWidth() * product.getHeight() * product.getDepth()) * quantity;
            deliveryWeight += product.getWeight() * quantity;
            fragile = product.isFragile() ? true : fragile;
        }
        orderBookingsRepository.saveAll(orderBookings);

        if (!productInShoppingCartLowQuantityInWarehouse.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse
                    (String.format("Ошибка, эти товары из корзины не находится в требуемом количестве на складе: %s",
                            productInShoppingCartLowQuantityInWarehouse));
        }
        return Mapper.mapToBookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }

    @Override
    @Transactional
    public void shippedToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest) {
        orderBookingsRepository.update(shippedToDeliveryRequest.orderId(), shippedToDeliveryRequest.deliveryId());
    }

    @Override
    @Transactional
    public void acceptReturnItems(Map<String, Long> products) {
        List<Product> productList = productRepository.findAllById(products.keySet());

        for (Product product : productList) {
            product.setQuantity(product.getQuantity() + products.get(product.getProductId()));
        }
    }
}