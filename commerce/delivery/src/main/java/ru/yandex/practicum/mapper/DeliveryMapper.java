package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.entity.Delivery;

public class DeliveryMapper {

    public static Delivery mapToDelivery(DeliveryDto dto) {
        return Delivery.builder()
                .deliveryState(dto.deliveryState())
                .orderId(dto.orderId())
                .build();
    }


    public static DeliveryDto mapToDeliveryDto(Delivery delivery) {
        return new DeliveryDto(
                delivery.getDeliveryId(),
                AddressMapper.mapToAddressDto(delivery.getFromAddress()),
                AddressMapper.mapToAddressDto(delivery.getToAddress()),
                delivery.getOrderId(),
                delivery.getDeliveryState());
    }
}