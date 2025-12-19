package ru.yandex.practicum.service.warehouseclient;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.contract.warehouse.Warehouse;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse", fallbackFactory = WarehouseClientFallbackFactory.class)
public interface WarehouseClient extends Warehouse {
}
