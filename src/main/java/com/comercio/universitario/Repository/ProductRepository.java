package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProductRepository extends JpaRepository<Product, Long> {
}
