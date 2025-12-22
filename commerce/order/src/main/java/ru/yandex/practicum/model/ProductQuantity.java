package ru.yandex.practicum.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_quantity")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;
}