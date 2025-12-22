package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
public interface DeliveryService {
    DeliveryDto planDelivery(DeliveryDto deliveryDto);

    BigDecimal deliveryCost(OrderDto orderDto);

    void acceptItemForDelivery(String orderId);

    void confirmDeliverySuccess(String orderId);

    void reportFailedDelivery(String orderId);
}
