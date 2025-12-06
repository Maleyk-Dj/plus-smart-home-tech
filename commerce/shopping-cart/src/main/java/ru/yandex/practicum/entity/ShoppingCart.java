package ru.yandex.practicum.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {
    @Id
    @Column(name = "shopping_cart_id", nullable = false, unique = true)
    private String shoppingCartId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ShoppingCartState state;

    @ToString.Exclude
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductQuantity> productQuantities;

    @PrePersist
    private void generatedId() {
        if (shoppingCartId == null) {
            shoppingCartId = UUID.randomUUID().toString();
        }
    }
}