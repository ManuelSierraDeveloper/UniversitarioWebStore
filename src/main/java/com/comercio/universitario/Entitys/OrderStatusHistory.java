package com.comercio.universitario.Entitys;

import com.comercio.universitario.Entitys.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "order_status_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_status")
    private Long idOrderStatus;

    @Column(name = "date_change")
    private LocalDate dateChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderStatus state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;
}