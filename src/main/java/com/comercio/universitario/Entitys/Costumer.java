package com.comercio.universitario.Entitys;

import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_state")
    private CostumerStatus state;

}