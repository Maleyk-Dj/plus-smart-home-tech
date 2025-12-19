package ru.yandex.practicum.service.orderclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.order.StateOrder;
import ru.yandex.practicum.exception.OrderServiceUnavailableException;

import java.util.List;

@Component
@Slf4j
public class OrderClientFallback implements OrderClient {
    @Override
    public List<OrderDto> getOrders(String username) {
        return List.of();
    }

    @Override
    public OrderDto addOrder(String username, CreateNewOrderRequest newOrder) {
        return null;
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        return null;
    }

    @Override
    public OrderDto payment(String orderId) {
        return null;
    }

    @Override
    public OrderDto updateOrderStatusPaymentFailed(String orderId) {
        return null;
    }

    @Override
    public OrderDto updateOrderStatusDelivery(String orderId, StateOrder state) {
        log.warn("Сервис заказов не работает. Невозможно получить данные из заказов");
        throw new OrderServiceUnavailableException("Сервис заказов не работает. Невозможно получить данные из " +
                "заказов");
    }

    @Override
    public OrderDto updateOrderStatusDeliveryFailed(String orderId) {
        return null;
    }

    @Override
    public OrderDto updateOrderStatusCompleted(String orderId) {
        return null;
    }

    @Override
    public OrderDto calculateTotal(String orderId) {
        return null;
    }

    @Override
    public OrderDto calculateDelivery(String orderId) {
        return null;
    }

    @Override
    public OrderDto assembly(String orderId) {
        return null;
    }

    @Override
    public OrderDto updateOrderStatusAssemblyFailed(String orderId) {
        return null;
    }
}