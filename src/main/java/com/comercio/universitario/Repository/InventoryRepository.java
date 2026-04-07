package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
