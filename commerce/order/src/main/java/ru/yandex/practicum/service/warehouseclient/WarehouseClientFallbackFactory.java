package ru.yandex.practicum.service.warehouseclient;


import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.InvalidRequestException;

@Component
@Slf4j
public class WarehouseClientFallbackFactory implements FallbackFactory<WarehouseClient> {
    @Override
    public WarehouseClient create(Throwable cause) {
        if (cause instanceof FeignException feignException) {
            int status = feignException.status();
            if (status == 400 || status == 403 || status == 404 || status == 409) {
                throw new InvalidRequestException(feignException.contentUTF8());
            } else if (status >= 500) {
                return new WarehouseClientFallback();
            }
        }
        return new WarehouseClientFallback();
    }
}