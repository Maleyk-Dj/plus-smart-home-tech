package ru.yandex.practicum.contract.order;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.order.StateOrder;

import java.util.List;

public interface Order {
    @GetMapping
    public List<OrderDto> getOrders(@RequestParam String username);

    @PutMapping
    public OrderDto addOrder(@RequestParam @NotBlank String username,
                             @RequestBody @Valid CreateNewOrderRequest newOrder);

    @PostMapping("/return")
    public OrderDto returnOrder(@RequestBody ProductReturnRequest productReturnRequest);

    @PostMapping("/payment")
    public OrderDto payment(@RequestBody @NotBlank String orderId);

    @PostMapping("/payment/failed")
    public OrderDto updateOrderStatusPaymentFailed(@RequestBody @NotBlank String orderId);

    @PostMapping("/delivery")
    public OrderDto updateOrderStatusDelivery(@RequestBody @NotBlank String orderId,
                                              @RequestParam @NotNull StateOrder state);

    @PostMapping("/delivery/failed")
    public OrderDto updateOrderStatusDeliveryFailed(@RequestBody @NotBlank String orderId);

    @PostMapping("/delivery/completed")
    public OrderDto updateOrderStatusCompleted(@RequestBody @NotBlank String orderId);

    @PostMapping("/calculate/total")
    public OrderDto calculateTotal(@RequestBody @NotBlank String orderId);

    @PostMapping("/calculate/delivery")
    public OrderDto calculateDelivery(@RequestBody @NotBlank String orderId);

    @PostMapping("/assembly")
    public OrderDto assembly(@RequestBody @NotBlank String orderId);

    @PostMapping("/assembly/failed")
    public OrderDto updateOrderStatusAssemblyFailed(@RequestBody @NotBlank String orderId);
}