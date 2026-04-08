package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategorie_IdCategorie(Long categoryId);

    Optional<Product> findBySku(String sku);

    List<Product> findByCategorie_IdCategorieAndActiveTrue(Long categoryId);
}
