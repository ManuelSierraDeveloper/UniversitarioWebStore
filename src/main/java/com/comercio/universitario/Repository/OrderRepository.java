package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderItem, Long> {
}
