package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.entity.Address;
import ru.yandex.practicum.entity.Delivery;

public class Mapper {
    public static Delivery mapToDelivery(DeliveryDto deliveryDto, Address fromAddress, Address toAddress) {
        return Delivery.builder()
                .deliveryVolume(0)
                .deliveryWeight(0)
                .fragile(true)
                .fromAddress(fromAddress)
                .toAddress(toAddress)
                .deliveryState(deliveryDto.deliveryState())
                .orderId(deliveryDto.orderId())
                .build();
    }

    public static Address mapToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.country())
                .city(addressDto.city())
                .street(addressDto.street())
                .house(addressDto.house())
                .flat(addressDto.flat())
                .build();
    }

    public static DeliveryDto mapToDeliveryDto(Delivery delivery) {
        return new DeliveryDto(
                delivery.getDeliveryId(),
                mapToAddressDto(delivery.getFromAddress()),
                mapToAddressDto(delivery.getToAddress()),
                delivery.getOrderId(),
                delivery.getDeliveryState());
    }

    public static AddressDto mapToAddressDto(Address address) {
        return new AddressDto(address.getCountry(), address.getCity(), address.getStreet(), address.getHouse(),
                address.getFlat());
    }
}