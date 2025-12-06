package ru.yandex.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.store.ProductCategory;
import ru.yandex.practicum.model.Product;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Product, String> {

    List<Product> getAllByProductCategory(ProductCategory productCategory);

    List<Product> getAllByProductCategory(ProductCategory productCategory, Pageable pageable);
    }
