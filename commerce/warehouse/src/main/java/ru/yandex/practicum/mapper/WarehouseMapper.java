package ru.yandex.practicum.mapper;


import  ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.entity.Product;


public class WarehouseMapper {
    public static Product mapToProduct(NewProductInWarehouseRequest newProductInWarehouseRequest) {
        return Product.builder()
                .productId(newProductInWarehouseRequest.productId())
                .fragile(newProductInWarehouseRequest.fragile())
                .width(newProductInWarehouseRequest.dimension().width())
                .height(newProductInWarehouseRequest.dimension().height())
                .depth(newProductInWarehouseRequest.dimension().depth())
                .weight(newProductInWarehouseRequest.weight())
                .build();
    }

    public static BookedProductsDto mapToBookedProductsDto(double deliveryWeight, double deliveryVolume,
                                                           boolean fragile) {
        return new BookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }
}