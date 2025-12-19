package ru.yandex.practicum.service.deliveryclient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.delivery.Delivery;

@FeignClient(name = "delivery", path = "/api/v1/delivery", fallbackFactory = DeliveryClientFallbackFactory.class)
public interface DeliveryClient extends Delivery {
}