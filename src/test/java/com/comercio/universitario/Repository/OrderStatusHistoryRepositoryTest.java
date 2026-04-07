package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import com.comercio.universitario.Entitys.Enums.OrderStatus;
import com.comercio.universitario.Entitys.Order;
import com.comercio.universitario.Entitys.OrderStatusHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderStatusHistoryRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CostomerRepository costomerRepository;

    @Test
    void shouldCreateReadUpdateDeleteOrderStatusHistory() {
        Costumer customer = costomerRepository.save(
                Costumer.builder()
                        .fullName("Cliente Estado")
                        .state(CostumerStatus.ACTIVE)
                        .build()
        );
        Order order = orderRepository.save(
                Order.builder()
                        .costumer(customer)
                        .stateOrder(OrderStatus.CREATED)
                        .date(LocalDate.of(2026, 4, 1))
                        .total(500L)
                        .build()
        );

        OrderStatusHistory saved = orderStatusHistoryRepository.save(
                OrderStatusHistory.builder()
                        .order(order)
                        .state(OrderStatus.CREATED)
                        .dateChange(LocalDate.of(2026, 4, 1))
                        .build()
        );

        assertThat(saved.getIdOrderStatus()).isNotNull();
        assertThat(orderStatusHistoryRepository.findById(saved.getIdOrderStatus())).isPresent();

        saved.setState(OrderStatus.PAID);
        OrderStatusHistory updated = orderStatusHistoryRepository.save(saved);

        assertThat(updated.getState()).isEqualTo(OrderStatus.PAID);

        orderStatusHistoryRepository.deleteById(updated.getIdOrderStatus());

        assertThat(orderStatusHistoryRepository.findById(updated.getIdOrderStatus())).isEmpty();
    }
}