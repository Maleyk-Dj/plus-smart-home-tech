package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.order.StateOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@NamedEntityGraph(
        name = "OrderWithProducts",
        attributeNodes = @NamedAttributeNode("products")
)
public class Order {
    @Id
    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StateOrder state;

    @Column(name = "shopping_cart_id", nullable = false)
    private String shoppingCartId;

    @Column(name = "delivery_id", nullable = false)
    private String deliveryId;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "delivery_volume", columnDefinition = "NUMERIC(15,3)")
    private double deliveryVolume;

    @Column(name = "delivery_weight", columnDefinition = "NUMERIC(10,3)")
    private double deliveryWeight;

    @Column
    private boolean fragile;

    @Column(name = "total_price", precision = 11, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "product_price", precision = 11, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "delivery_price", precision = 11, scale = 2)
    private BigDecimal deliveryPrice;

    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductQuantity> products;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @PrePersist
    public void generatedId() {
        if (orderId == null) {
            orderId = UUID.randomUUID().toString();
        }
    }
}