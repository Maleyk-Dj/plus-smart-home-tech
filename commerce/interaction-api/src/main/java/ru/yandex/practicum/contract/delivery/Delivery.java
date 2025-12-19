package ru.yandex.practicum.contract.delivery;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;

public interface Delivery {

    @PutMapping
    DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto);

    @PostMapping("/cost")
    BigDecimal deliveryCost(@RequestBody @Valid OrderDto orderDto);

    @PostMapping("/picked")
    void acceptItemForDelivery(@RequestBody @NotBlank String orderId);

    @PostMapping("/successful")
    void confirmDeliverySuccess(@RequestBody @NotBlank String orderId);

    @PostMapping("/failed")
    void reportFailedDelivery(@RequestBody @NotBlank String orderId);
}