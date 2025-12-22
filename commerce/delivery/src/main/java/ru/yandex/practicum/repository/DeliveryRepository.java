package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Delivery;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {

        Optional<Delivery> findByOrderId(String orderId);

        @EntityGraph("DeliveryWithAddress")
        Optional<Delivery> findByDeliveryId(String deliveryId);
    }