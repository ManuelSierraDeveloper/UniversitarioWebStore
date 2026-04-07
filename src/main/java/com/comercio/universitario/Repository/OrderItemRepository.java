package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
