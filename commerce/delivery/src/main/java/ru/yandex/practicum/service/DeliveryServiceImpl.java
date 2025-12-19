package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.StateOrder;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.entity.Address;
import ru.yandex.practicum.entity.Delivery;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.contract.warehouse.Warehouse;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.repository.AddressRepository;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.service.orderclient.OrderClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryServiceImpl implements DeliveryService {
    private final OrderClient orderClient;
    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final Warehouse warehouseClient;
    private static final String[] ADDRESS = new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final BigDecimal BASE_COST = new BigDecimal(5);
    private static final BigDecimal WAREHOUSE_COEFFICIENT_AT_ADDRESS_1 = new BigDecimal(1);
    private static final BigDecimal WAREHOUSE_COEFFICIENT_AT_ADDRESS_2 = new BigDecimal(2);
    private static final BigDecimal FRAGILITY_COEFFICIENT = new BigDecimal("0.2");
    private static final BigDecimal WEIGHT_COEFFICIENT = new BigDecimal("0.3");
    private static final BigDecimal VOLUME_COEFFICIENT = new BigDecimal("0.2");
    private static final BigDecimal SHOPPING_ADDRESS_COEFFICIENT = new BigDecimal("0.2");

    @Override
    @Transactional
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Address fromAddress = addressRepository.save(Mapper.mapToAddress(deliveryDto.fromAddress()));
        Address toAddress = addressRepository.save(Mapper.mapToAddress(deliveryDto.toAddress()));
        Delivery delivery = deliveryRepository.save(Mapper.mapToDelivery(deliveryDto, fromAddress, toAddress));
        return Mapper.mapToDeliveryDto(delivery);
    }

    @Override
    public BigDecimal deliveryCost(OrderDto orderDto) {
        Delivery delivery = deliveryRepository.findByDeliveryId(orderDto.deliveryId())
                .orElseThrow(() ->
                        new NoDeliveryFoundException(String.format("Нет доставки с id %s", orderDto.deliveryId())));

        BigDecimal result = BigDecimal.ZERO;
        if (delivery.getFromAddress().getCountry().equals(ADDRESS[0])) {
            result = BASE_COST.multiply(WAREHOUSE_COEFFICIENT_AT_ADDRESS_1);
        } else if (delivery.getFromAddress().getCountry().equals(ADDRESS[1])) {
            result = (BASE_COST.multiply(WAREHOUSE_COEFFICIENT_AT_ADDRESS_2))
                    .add(BASE_COST);
        }
        if (delivery.isFragile()) {
            result = result.multiply(FRAGILITY_COEFFICIENT)
                    .add(result);
        }
        result = new BigDecimal(Double.toString(delivery.getDeliveryWeight()))
                .multiply(WEIGHT_COEFFICIENT)
                .add(result);
        result = new BigDecimal(Double.toString(delivery.getDeliveryVolume()))
                .multiply(VOLUME_COEFFICIENT)
                .add(result);
        if (!delivery.getToAddress().getCountry().equals(delivery.getFromAddress().getCountry())) {
            result = result.multiply(SHOPPING_ADDRESS_COEFFICIENT)
                    .add(result).setScale(2, RoundingMode.HALF_EVEN);
        }
        return result;
    }

    @Override
    @Transactional
    public void acceptItemForDelivery(String orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new NoDeliveryFoundException(String.format("Нет доставки с id %s", orderId)));
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);

        warehouseClient.shippedToDelivery(new ShippedToDeliveryRequest(delivery.getOrderId(),
                delivery.getDeliveryId()));

        orderClient.updateOrderStatusDelivery(orderId, StateOrder.ASSEMBLED);
    }

    @Override
    @Transactional
    public void confirmDeliverySuccess(String orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new NoDeliveryFoundException(String.format("Нет доставки с id %s", orderId)));
        delivery.setDeliveryState(DeliveryState.DELIVERED);

        orderClient.updateOrderStatusDelivery(orderId, StateOrder.DELIVERED);
    }

    @Override
    @Transactional
    public void reportFailedDelivery(String orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new NoDeliveryFoundException(String.format("Нет доставки с id %s", orderId)));
        delivery.setDeliveryState(DeliveryState.FAILED);

        orderClient.updateOrderStatusDelivery(orderId, StateOrder.DELIVERY_FAILED);
    }
}