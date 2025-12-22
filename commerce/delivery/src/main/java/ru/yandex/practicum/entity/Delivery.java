package ru.yandex.practicum.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.delivery.DeliveryState;

import java.util.UUID;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@NamedEntityGraph(
        name = "DeliveryWithAddress",
        attributeNodes = {
                @NamedAttributeNode("fromAddress"),
                @NamedAttributeNode("toAddress")
        }
)
public class Delivery {
    @Id
    @Column(name = "delivery_id", nullable = false, unique = true)
    private String deliveryId;

    @Column(name = "delivery_volume", nullable = false)
    private double deliveryVolume;

    @Column(name = "delivery_weight", nullable = false)
    private double deliveryWeight;

    @Column(name = "fragile", nullable = false)
    private boolean fragile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address_id")
    @ToString.Exclude
    private Address fromAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_address_id")
    @ToString.Exclude
    private Address toAddress;

    @Column(name = "order_id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_state")
    private DeliveryState deliveryState;

    @PrePersist
    public void setDeliveryId() {
        if (deliveryId == null) {
            deliveryId = UUID.randomUUID().toString();
        }
    }
}