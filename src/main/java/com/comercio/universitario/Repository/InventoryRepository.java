package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("select i from Inventory i where i.cantStockAvailable <= i.cantStockMinimun")
    List<Inventory> findLowStockProducts();
}