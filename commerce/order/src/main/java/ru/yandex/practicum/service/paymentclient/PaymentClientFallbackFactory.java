package ru.yandex.practicum.service.paymentclient;


import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.InvalidRequestException;

@Component
public class PaymentClientFallbackFactory implements FallbackFactory<PaymentClient> {

    @Override
    public PaymentClient create(Throwable cause) {
        if (cause instanceof FeignException feignException) {
            int status = feignException.status();
            if (status == 400 || status == 403 || status == 404 || status == 409) {
                throw new InvalidRequestException(feignException.contentUTF8());
            } else if (status >= 500) {
                return new PaymentClientFallback();
            }
        }
        return new PaymentClientFallback();
    }
}
