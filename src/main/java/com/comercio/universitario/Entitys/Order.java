package com.comercio.universitario.Entitys;

import com.comercio.universitario.Entitys.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_costumer")
    private Costumer costumer;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_order")
    private OrderStatus stateOrder;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total")
    private Long total;
}