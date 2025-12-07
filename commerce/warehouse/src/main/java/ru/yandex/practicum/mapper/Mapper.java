package ru.yandex.practicum.mapper;


import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductlnWarehouseRequest;
import ru.yandex.practicum.entity.Product;


public class Mapper {
    public static Product mapToProduct(NewProductlnWarehouseRequest newProductInWarehouseRequest) {
        return Product.builder()
                .productId(newProductInWarehouseRequest.getProductId())
                .fragile(newProductInWarehouseRequest.getFragile())
                .width(newProductInWarehouseRequest.getDimension().getWidth())
                .height(newProductInWarehouseRequest.getDimension().getHeight())
                .depth(newProductInWarehouseRequest.getDimension().getDepth())
                .weight(newProductInWarehouseRequest.getWeight())
                //            .quantity(0)
                .build();
    }

    public static BookedProductsDto mapToBookedProductsDto(double deliveryWeight, double deliveryVolume,
                                                           boolean fragile) {

        return new BookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }
}