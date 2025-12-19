package ru.yandex.practicum.service.paymentclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.exception.WarehouseServiceUnavailableException;

import java.math.BigDecimal;

@Component
@Slf4j
public class PaymentClientFallback implements PaymentClient {
    @Override
    public PaymentDto payment(OrderDto orderDto) {
        return null;
    }

    @Override
    public BigDecimal getTotalCost(OrderDto orderDto) {
        log.warn("Сервис оплаты не работает. Невозможно рассчитать стоимость товаров, доставки и налога");
        throw new WarehouseServiceUnavailableException("Сервис оплаты не работает. Невозможно рассчитать стоимость " +
                "товаров, доставки и налога");
    }

    @Override
    public void refund(String paymentId) {
    }

    @Override
    public BigDecimal productCost(OrderDto orderDto) {
        log.warn("Сервис оплаты не работает. Невозможно рассчитать стоимость товаров");
        throw new WarehouseServiceUnavailableException("Сервис оплаты не работает. Невозможно рассчитать стоимость " +
                "товаров");
    }

    @Override
    public void emulationInFailure(String paymentId) {
    }
}