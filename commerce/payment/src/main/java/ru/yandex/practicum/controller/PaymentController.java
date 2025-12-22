package ru.yandex.practicum.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.contract.payment.Payment;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;
import ru.yandex.practicum.util.Loggable;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Validated
public class PaymentController implements Payment {
    private final PaymentService paymentService;

    @Override
    @PostMapping
    @Loggable
    public PaymentDto payment(@RequestBody OrderDto orderDto) {
        return paymentService.paymentFormation(orderDto);
    }

    @Override
    @PostMapping("/totalCost")
    @Loggable
    public BigDecimal getTotalCost(@RequestBody OrderDto orderDto) {
        return paymentService.getTotalCost(orderDto);
    }

    @Override
    @PostMapping("refund")
    @Loggable
    public void refund(@RequestBody @NotBlank String paymentId) {
        paymentService.refund(paymentId);
    }

    @Override
    @PostMapping("/productCost")
    @Loggable
    public BigDecimal productCost(@RequestBody OrderDto orderDto) {
        return paymentService.productCost(orderDto);
    }

    @Override
    @PostMapping("/failed")
    @Loggable
    public void emulationInFailure(@RequestBody @NotBlank String paymentId) {
        paymentService.emulationInFailure(paymentId);
    }
}