package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.payment.StatusPayment;
import ru.yandex.practicum.dto.store.ProductDto;
import ru.yandex.practicum.exception.NoPaymentFoundException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.service.orderclient.OrderClient;
import ru.yandex.practicum.service.storeclient.ShoppingStoreClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto paymentFormation(OrderDto orderDto) {
        Payment payment = paymentRepository.save(PaymentMapper.mapToPayment(orderDto));
        return PaymentMapper.mapToPaymentDto(payment);
    }

    @Override
    public BigDecimal getTotalCost(OrderDto orderDto) {
        BigDecimal productPrice = orderDto.productPrice();
        BigDecimal deliveryPrice = orderDto.deliveryPrice();
        BigDecimal feeTotal = productPrice.divide(BigDecimal.TEN, RoundingMode.HALF_EVEN);
        return productPrice.add(feeTotal).add(deliveryPrice);
    }

    @Override
    @Transactional
    public void refund(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoPaymentFoundException(String.format("Платёж с id %s не найден", paymentId)));
        payment.setStatusPayment(StatusPayment.SUCCESS);
        orderClient.payment(payment.getOrderId());
    }

    @Override
    public BigDecimal productCost(OrderDto orderDto) {
        Map<String, Long> products = orderDto.products();

        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<String, Long> product : products.entrySet()) {
            BigDecimal count = BigDecimal.valueOf(product.getValue());
            String productId = product.getKey();

            ProductDto productDto = shoppingStoreClient.getProduct(productId);
            BigDecimal price = productDto.price();

            totalCost = count.multiply(price)
                    .add(totalCost);
        }
        return totalCost;
    }

    @Override
    public void emulationInFailure(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoPaymentFoundException(String.format("Платёж с id %s не найден", paymentId)));
        payment.setStatusPayment(StatusPayment.FAILED);
        orderClient.updateOrderStatusPaymentFailed(payment.getOrderId());
    }
}