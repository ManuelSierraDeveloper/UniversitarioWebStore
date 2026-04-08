package com.comercio.universitario.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_products")
    private Long idProducts;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "sku", unique = true)
    private String sku;

    @Builder.Default
    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie")
    private Category categorie;
}
