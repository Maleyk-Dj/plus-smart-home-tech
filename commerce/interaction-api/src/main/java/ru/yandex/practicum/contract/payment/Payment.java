package ru.yandex.practicum.contract.payment;


import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.math.BigDecimal;

public interface Payment {
    @PostMapping
    PaymentDto payment(@RequestBody OrderDto orderDto);

    @PostMapping("/totalCost")
    BigDecimal getTotalCost(@RequestBody OrderDto orderDto);

    @PostMapping("refund")
    void refund(@RequestBody @NotBlank String paymentId);

    @PostMapping("/productCost")
    BigDecimal productCost(@RequestBody OrderDto orderDto);

    @PostMapping("/failed")
    void emulationInFailure(@RequestBody @NotBlank String paymentId);
}