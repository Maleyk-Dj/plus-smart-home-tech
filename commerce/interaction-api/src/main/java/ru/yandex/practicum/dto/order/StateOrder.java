package ru.yandex.practicum.dto.order;

/**
 * Enum состояний заказа — соответствует перечислению в OpenAPI-спецификации.
 */
public enum StateOrder {
    NEW,
    ON_PAYMENT,
    ON_DELIVERY,
    DONE,
    DELIVERED,
    ASSEMBLED,
    PAID,
    COMPLETED,
    DELIVERY_FAILED,
    ASSEMBLY_FAILED,
    PAYMENT_FAILED,
    PRODUCT_RETURNED,
    CANCELED
}