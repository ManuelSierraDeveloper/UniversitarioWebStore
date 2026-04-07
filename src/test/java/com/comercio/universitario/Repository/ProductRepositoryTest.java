package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import com.comercio.universitario.Entitys.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindProductsByCategory() {
        Category technology = categoryRepository.save(Category.builder().nameCategorie("Tecnologia").build());
        Category books = categoryRepository.save(Category.builder().nameCategorie("Libros").build());

        productRepository.save(Product.builder().nameProduct("Laptop").categorie(technology).build());
        productRepository.save(Product.builder().nameProduct("Mouse").categorie(technology).build());
        productRepository.save(Product.builder().nameProduct("Clean Code").categorie(books).build());

        List<Product> products = productRepository.findByCategorie_IdCategorie(technology.getIdCategorie());

        assertThat(products).hasSize(2);
        assertThat(products)
                .extracting(Product::getNameProduct)
                .containsExactlyInAnyOrder("Laptop", "Mouse");
    }
}