package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.order.StateOrder;

import java.util.List;


public interface OrderService {
    List<OrderDto> getOrders(String username);

    OrderDto addOrder(CreateNewOrderRequest newOrder, String username);

    OrderDto returnOrder(ProductReturnRequest productReturnRequest);

    OrderDto payment(String orderId);

    OrderDto updateOrderStatusPaymentFailed(String orderId);

    OrderDto updateOrderStatusDelivery(String orderId, StateOrder state);

    OrderDto updateOrderStatusDeliveryFailed(String orderId);

    OrderDto updateOrderStatusCompleted(String orderId);

    OrderDto calculateTotal(String orderId);

    OrderDto calculateDelivery(String orderId);

    OrderDto assembly(String orderId);

    OrderDto updateOrderStatusAssemblyFailed(String orderId);
}