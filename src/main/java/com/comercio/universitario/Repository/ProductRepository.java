package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategorie_IdCategorie(Long categoryId);
}