package ru.yandex.practicum.service.storeclient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.shoppingstore.ShoppingStore;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store",
        fallbackFactory = ShoppingStoreClientFallbackFactory.class)
public interface ShoppingStoreClient extends ShoppingStore {
}