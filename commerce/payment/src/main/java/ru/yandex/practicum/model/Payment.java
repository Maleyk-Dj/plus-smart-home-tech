package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.payment.StatusPayment;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "payments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @Column(name = "payment_id", nullable = false, unique = true)
    private String paymentId;

    @Column(name = "total_payment", nullable = false, precision = 11, scale = 2)
    private BigDecimal totalPayment;

    @Column(name = "product_price", nullable = false, precision = 11, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "delivery_total", nullable = false, precision = 11, scale = 2)
    private BigDecimal deliveryTotal;

    @Column(name = "fee_total", nullable = false, precision = 11, scale = 2)
    private BigDecimal feeTotal;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_payment")
    private StatusPayment statusPayment;

    @Column(name = "order_id")
    private String orderId;

    @PrePersist
    private void setPaymentId() {
        paymentId = UUID.randomUUID().toString();
    }
}