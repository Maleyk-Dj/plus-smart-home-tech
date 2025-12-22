package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.entity.Product;


public interface ProductRepository extends JpaRepository<Product, String> {
    @Modifying
    @Query("""
            UPDATE Product p
            SET p.quantity = :quantity
            WHERE p.productId = :productId
            """)
    void updateProduct(@Param(value = "productId") String productId, @Param(value = "quantity") String quantity);
}