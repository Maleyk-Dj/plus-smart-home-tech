package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;

import ru.yandex.practicum.dto.order.StateOrder;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.ProductQuantity;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public static List<ProductQuantity> mapToProductQuantity(CreateNewOrderRequest newOrder, Order order) {
        return newOrder.shoppingCart().products().entrySet().stream()
                .map(product -> ProductQuantity.builder()
                        .productId(product.getKey())
                        .quantity(product.getValue())
                        .order(order)
                        .build())
                .toList();
    }

    public static Order mapToOrder(CreateNewOrderRequest newOrder, String userName) {
        return Order.builder()
                .state(StateOrder.NEW)
                .shoppingCartId(newOrder.shoppingCart().shoppingCartId())
                .deliveryId("")
                .paymentId("")
                .deliveryVolume(0)
                .deliveryWeight(0)
                .fragile(false)
                .totalPrice(BigDecimal.ZERO)
                .productPrice(BigDecimal.ZERO)
                .deliveryPrice(BigDecimal.ZERO)
                .userName(userName)
                .build();
    }

    public static OrderDto mapToOrderDto(Order order, Map<String, Long> products) {
        return new OrderDto(
                order.getOrderId(),
                order.getShoppingCartId(),
                products,
                order.getPaymentId(),
                order.getDeliveryId(),
                order.getState(),
                order.getDeliveryWeight(),
                order.getDeliveryVolume(),
                order.isFragile(),
                order.getTotalPrice(),
                order.getDeliveryPrice(),
                order.getProductPrice()
        );
    }

    public static AssemblyProductsForOrderRequest mapToAssemblyProducts(List<ProductQuantity> product, String orderId) {
        return new AssemblyProductsForOrderRequest(product
                .stream()
                .collect(Collectors.toMap(ProductQuantity::getProductId, ProductQuantity::getQuantity)), orderId);
    }

    public static OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getShoppingCartId(),
                order.getProducts().stream().collect(Collectors.toMap(ProductQuantity::getProductId,
                        ProductQuantity::getQuantity)),
                order.getPaymentId(),
                order.getDeliveryId(),
                order.getState(),
                order.getDeliveryWeight(),
                order.getDeliveryVolume(),
                order.isFragile(),
                order.getTotalPrice(),
                order.getDeliveryPrice(),
                order.getProductPrice()
        );
    }
}
