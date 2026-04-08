package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import com.comercio.universitario.Entitys.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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

    @Test
    void shouldFindProductBySku() {
        Category category = categoryRepository.save(Category.builder().nameCategorie("Tecnologia").build());
        Product saved = productRepository.save(
                Product.builder()
                        .nameProduct("Laptop")
                        .sku("LAP-001")
                        .categorie(category)
                        .build()
        );

        Optional<Product> found = productRepository.findBySku("LAP-001");

        assertThat(found).isPresent();
        assertThat(found.get().getIdProducts()).isEqualTo(saved.getIdProducts());
    }

    @Test
    void shouldFindActiveProductsByCategory() {
        Category category = categoryRepository.save(Category.builder().nameCategorie("Tecnologia").build());

        productRepository.save(Product.builder().nameProduct("Activo 1").sku("ACT-1").active(true).categorie(category).build());
        productRepository.save(Product.builder().nameProduct("Activo 2").sku("ACT-2").active(true).categorie(category).build());
        productRepository.save(Product.builder().nameProduct("Inactivo").sku("INA-1").active(false).categorie(category).build());

        List<Product> products = productRepository.findByCategorie_IdCategorieAndActiveTrue(category.getIdCategorie());

        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getNameProduct).containsExactlyInAnyOrder("Activo 1", "Activo 2");
    }
}
