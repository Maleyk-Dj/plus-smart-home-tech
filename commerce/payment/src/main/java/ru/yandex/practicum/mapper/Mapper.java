package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.payment.StatusPayment;
import ru.yandex.practicum.model.Payment;



@Component
public class Mapper {
    public static Payment mapToPayment(OrderDto orderDto) {
        return Payment.builder()
                .totalPayment(orderDto.totalPrice())
                .productPrice(orderDto.productPrice())
                .deliveryTotal(orderDto.deliveryPrice())
                .feeTotal(orderDto.totalPrice().subtract(orderDto.productPrice()).subtract(orderDto.deliveryPrice()))
                .statusPayment(StatusPayment.PENDING)
                .orderId(orderDto.orderId())
                .build();
    }

    public static PaymentDto mapToPaymentDto(Payment payment) {
        return new PaymentDto(payment.getPaymentId(), payment.getTotalPayment(), payment.getDeliveryTotal(),
                payment.getFeeTotal());
    }
}
