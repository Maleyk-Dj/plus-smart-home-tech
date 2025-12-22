package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.entity.OrderBooking;

public interface OrderBookingsRepository extends JpaRepository<OrderBooking, Long> {

    @Modifying
    @Query(
            """
                    UPDATE OrderBooking o
                    SET o.deliveryId = :deliveryId
                    WHERE o.orderId = :orderId
                    """
    )
    void update(@Param(value = "orderId") String orderId, @Param(value = "deliveryId") String deliveryId);
}
