package ru.yandex.practicum.service.orderclient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.order.Order;

@FeignClient(name = "order", path = "/api/v1/order", fallbackFactory = OrderClientFallbackFactory.class)
public interface OrderClient extends Order {
}