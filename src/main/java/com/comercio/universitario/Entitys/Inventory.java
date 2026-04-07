package com.comercio.universitario.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventories")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventorie")
    private Long idInventorie;

    @Column(name = "cant_stock_available")
    private Long cantStockAvailable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "cant_stock_minimun")
    private Long cantStockMinimun;
}