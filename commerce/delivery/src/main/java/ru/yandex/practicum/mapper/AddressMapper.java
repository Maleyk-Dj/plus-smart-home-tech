package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.entity.Address;

public class AddressMapper {
    public static Address mapToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.country())
                .city(addressDto.city())
                .street(addressDto.street())
                .house(addressDto.house())
                .flat(addressDto.flat())
                .build();
    }

    public static AddressDto mapToAddressDto(Address address) {
        return new AddressDto(address.getCountry(), address.getCity(), address.getStreet(), address.getHouse(),
                address.getFlat());
    }
}
