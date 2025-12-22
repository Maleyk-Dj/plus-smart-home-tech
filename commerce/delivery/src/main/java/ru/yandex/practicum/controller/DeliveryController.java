package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.contract.delivery.Delivery;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;
import ru.yandex.practicum.util.Loggable;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController implements Delivery {
    private final DeliveryService deliveryService;

    @Override
    @PutMapping
    @Loggable
    public DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        return deliveryService.planDelivery(deliveryDto);
    }

    @Override
    @PostMapping("/cost")
    @Loggable
    public BigDecimal deliveryCost(@RequestBody @Valid OrderDto orderDto) {
        return deliveryService.deliveryCost(orderDto);
    }

    @Override
    @PostMapping("/picked")
    @Loggable
    public void acceptItemForDelivery(@RequestBody @NotBlank String orderId) {
        deliveryService.acceptItemForDelivery(orderId);
    }

    @Override
    @PostMapping("/successful")
    @Loggable
    public void confirmDeliverySuccess(@RequestBody @NotBlank String orderId) {
        deliveryService.confirmDeliverySuccess(orderId);
    }

    @Override
    @PostMapping("/failed")
    @Loggable
    public void reportFailedDelivery(@RequestBody @NotBlank String orderId) {
        deliveryService.reportFailedDelivery(orderId);
    }
}