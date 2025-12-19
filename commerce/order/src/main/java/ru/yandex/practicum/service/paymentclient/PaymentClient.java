package ru.yandex.practicum.service.paymentclient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.payment.Payment;

@FeignClient(name = "payment", path = "/api/v1/payment", fallbackFactory = PaymentClientFallbackFactory.class)
public interface PaymentClient extends Payment {
}
