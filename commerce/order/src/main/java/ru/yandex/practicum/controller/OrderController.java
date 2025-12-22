package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.contract.order.Order;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.order.StateOrder;
import ru.yandex.practicum.service.OrderService;
import ru.yandex.practicum.util.Loggable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Validated
public class OrderController implements Order {
    private final OrderService orderService;

    @Override
    @GetMapping
    @Loggable
    public List<OrderDto> getOrders(@RequestParam String username) {
        return orderService.getOrders(username);
    }

    @Override
    @PutMapping
    @Loggable
    public OrderDto addOrder(@RequestParam @NotBlank String username,
                             @RequestBody @Valid CreateNewOrderRequest newOrder) {
        return orderService.addOrder(newOrder, username);
    }

    @Override
    @PostMapping("/return")
    @Loggable
    public OrderDto returnOrder(@RequestBody ProductReturnRequest productReturnRequest) {
        return orderService.returnOrder(productReturnRequest);
    }

    @Override
    @PostMapping("/payment")
    @Loggable
    public OrderDto payment(@RequestBody @NotBlank String orderId) {
        return orderService.payment(orderId);
    }

    @Override
    @PostMapping("/payment/failed")
    @Loggable
    public OrderDto updateOrderStatusPaymentFailed(@RequestBody @NotBlank String orderId) {
        return orderService.updateOrderStatusPaymentFailed(orderId);
    }

    @Override
    @PostMapping("/delivery")
    @Loggable
    public OrderDto updateOrderStatusDelivery(@RequestBody @NotBlank String orderId,
                                              @RequestParam @NotNull StateOrder state) {
        return orderService.updateOrderStatusDelivery(orderId, state);
    }

    @Override
    @PostMapping("/delivery/failed")
    @Loggable
    public OrderDto updateOrderStatusDeliveryFailed(@RequestBody @NotBlank String orderId) {
        return orderService.updateOrderStatusDeliveryFailed(orderId);
    }

    @Override
    @PostMapping("/delivery/completed")
    @Loggable
    public OrderDto updateOrderStatusCompleted(@RequestBody @NotBlank String orderId) {
        return orderService.updateOrderStatusCompleted(orderId);
    }

    @Override
    @PostMapping("/calculate/total")
    @Loggable
    public OrderDto calculateTotal(@RequestBody @NotBlank String orderId) {
        return orderService.calculateTotal(orderId);
    }

    @Override
    @PostMapping("/calculate/delivery")
    @Loggable
    public OrderDto calculateDelivery(@RequestBody @NotBlank String orderId) {
        return orderService.calculateDelivery(orderId);
    }

    @Override
    @PostMapping("/assembly")
    @Loggable
    public OrderDto assembly(@RequestBody @NotBlank String orderId) {
        return orderService.assembly(orderId);
    }

    @Override
    @PostMapping("/assembly/failed")
    @Loggable
    public OrderDto updateOrderStatusAssemblyFailed(@RequestBody @NotBlank String orderId) {
        return orderService.updateOrderStatusAssemblyFailed(orderId);
    }
}