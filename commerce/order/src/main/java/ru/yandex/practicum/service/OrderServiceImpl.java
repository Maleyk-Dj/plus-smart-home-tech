package ru.yandex.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.delivery.DeliveryState;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.order.StateOrder;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.ProductQuantity;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.service.deliveryclient.DeliveryClient;
import ru.yandex.practicum.service.paymentclient.PaymentClient;
import ru.yandex.practicum.service.warehouseclient.WarehouseClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;
    private final TransactionTemplate transactionTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrders(String username) {
        if (username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }

        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = orderRepository.findAllByUserName(username, pageable);

        return orders.stream().map(OrderMapper::mapToOrderDto).toList();
    }

    @Override
    @Transactional
    public OrderDto addOrder(CreateNewOrderRequest newOrder, String username) {
        Order order = orderRepository.save(OrderMapper.mapToOrder(newOrder, username));
        order.setProducts(OrderMapper.mapToProductQuantity(newOrder, order));

        AddressDto toAddressDto = newOrder.deliveryAddress();
        AddressDto fromAddressDto = warehouseClient.getWarehouseAddress();

        DeliveryDto deliveryDto = deliveryClient.planDelivery(new DeliveryDto("", fromAddressDto,
                toAddressDto, order.getOrderId(), DeliveryState.CREATED));

        order.setDeliveryId(deliveryDto.deliveryId());
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        Order order = orderRepository.findByOrderId(productReturnRequest.orderId())
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказа с id = %s нет",
                        productReturnRequest.orderId())));

        Map<String, Long> productsInTheOrder = order.getProducts().stream()
                .collect(Collectors.toMap(ProductQuantity::getProductId, ProductQuantity::getQuantity));
        Map<String, Long> canceledProducts = productReturnRequest.products();

        boolean availableOnOrder = productsInTheOrder.entrySet().stream()
                .allMatch(productQuantity -> canceledProducts.containsKey(productQuantity.getKey())
                        && canceledProducts.get(productQuantity.getKey()).equals(productQuantity.getValue()));

        if (!availableOnOrder) {
            throw new NoProductsInShoppingCartException("Товары для возврата не соответствуют заказу");
        }
        order.setState(StateOrder.PRODUCT_RETURNED);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto payment(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        paymentClient.payment(OrderMapper.mapToOrderDto(order));
        order.setState(StateOrder.PAID);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatusPaymentFailed(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        order.setState(StateOrder.PAYMENT_FAILED);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatusDelivery(String orderId, StateOrder state) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        order.setState(state);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatusDeliveryFailed(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        order.setState(StateOrder.DELIVERY_FAILED);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatusCompleted(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        order.setState(StateOrder.COMPLETED);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateTotal(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));
        BigDecimal productCost = paymentClient.productCost(OrderMapper.mapToOrderDto(order));
        order.setProductPrice(productCost);
        BigDecimal totalCost = paymentClient.getTotalCost(OrderMapper.mapToOrderDto(order));
        order.setTotalPrice(totalCost);
        order.setState(StateOrder.ON_PAYMENT);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateDelivery(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));
        BigDecimal deliveryPrice = deliveryClient.deliveryCost(OrderMapper.mapToOrderDto(order));
        order.setDeliveryPrice(deliveryPrice);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto assembly(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        BookedProductsDto bookedProductsDto = warehouseClient.prepareItemsForShipment(
                OrderMapper.mapToAssemblyProducts(order.getProducts(), order.getOrderId()));

        order.setDeliveryWeight(bookedProductsDto.deliveryWeight());
        order.setDeliveryVolume(bookedProductsDto.deliveryVolume());
        order.setFragile(bookedProductsDto.fragile());
        order.setState(StateOrder.ASSEMBLED);
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatusAssemblyFailed(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(String.format("Заказ с id = %s не найден",
                        orderId)));

        order.setState(StateOrder.ASSEMBLY_FAILED);
        return OrderMapper.mapToOrderDto(order);
    }
}