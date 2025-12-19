package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDto paymentFormation(OrderDto orderDto);

    BigDecimal getTotalCost(OrderDto orderDto);

    void refund(String paymentId);

    BigDecimal productCost(OrderDto orderDto);

    void emulationInFailure(String paymentId);
}