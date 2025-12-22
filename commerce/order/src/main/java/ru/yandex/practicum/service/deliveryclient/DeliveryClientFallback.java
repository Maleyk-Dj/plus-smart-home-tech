package ru.yandex.practicum.service.deliveryclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.exception.WarehouseServiceUnavailableException;

import java.math.BigDecimal;

@Component
@Slf4j
public class DeliveryClientFallback implements DeliveryClient {
    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        log.warn("Сервис доставки не работает. Невозможно создать доставки");
        throw new WarehouseServiceUnavailableException("Сервис доставки не работает. Невозможно создать доставки");
    }

    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        log.warn("Сервис доставки не работает. Невозможно рассчитать полную стоимости доставки заказа");
        throw new WarehouseServiceUnavailableException("Сервис доставки не работает. Невозможно рассчитать полную " +
                "стоимости доставки заказа");
    }

    @Override
    public void acceptItemForDelivery(String orderId) {
    }

    @Override
    public void confirmDeliverySuccess(String orderId) {
    }

    @Override
    public void reportFailedDelivery(String orderId) {
    }
}