package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Category;
import com.comercio.universitario.Entitys.Inventory;
import com.comercio.universitario.Entitys.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindLowStockProducts() {
        Category category = categoryRepository.save(Category.builder().nameCategorie("General").build());

        Product lowStockProduct = productRepository.save(Product.builder().nameProduct("Cuaderno").categorie(category).build());
        Product minStockProduct = productRepository.save(Product.builder().nameProduct("Lapiz").categorie(category).build());
        Product normalStockProduct = productRepository.save(Product.builder().nameProduct("Borrador").categorie(category).build());

        inventoryRepository.save(Inventory.builder().product(lowStockProduct).cantStockAvailable(2L).cantStockMinimun(5L).build());
        inventoryRepository.save(Inventory.builder().product(minStockProduct).cantStockAvailable(5L).cantStockMinimun(5L).build());
        inventoryRepository.save(Inventory.builder().product(normalStockProduct).cantStockAvailable(8L).cantStockMinimun(5L).build());

        List<Inventory> inventories = inventoryRepository.findLowStockProducts();

        assertThat(inventories).hasSize(2);
        assertThat(inventories)
                .extracting(inventory -> inventory.getProduct().getNameProduct())
                .containsExactlyInAnyOrder("Cuaderno", "Lapiz");
    }
}