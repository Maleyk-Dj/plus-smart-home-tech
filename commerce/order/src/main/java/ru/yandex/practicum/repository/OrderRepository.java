package ru.yandex.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.yandex.practicum.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    @EntityGraph("OrderWithProducts")
    List<Order> findAllByUserName(String username, Pageable pageable);

    @EntityGraph("OrderWithProducts")
    Optional<Order> findByOrderId(String orderId);
}
