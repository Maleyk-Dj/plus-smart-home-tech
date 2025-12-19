package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.practicum.dto.store.ProductCategory;
import ru.yandex.practicum.dto.store.ProductState;
import ru.yandex.practicum.dto.store.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
    @Id
    @Column(nullable = false, unique = true)
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false, length = 1000)
    private String description;
    private String imageSrc;
    @Enumerated(EnumType.STRING)
    private QuantityState quantityState;
    @Enumerated(EnumType.STRING)
    private ProductState productState;
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal price;

    @PrePersist
    private void generateId() {
        if (productId == null) {
            productId = UUID.randomUUID().toString();
        }
    }
}