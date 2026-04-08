package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrder_IdOrderOrderByDateChangeAsc(Long orderId);
}
