package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CostumerRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private CostomerRepository costomerRepository;

    @Test
    void shouldCreateReadUpdateDeleteCustomer() {
        Costumer saved = costomerRepository.save(
                Costumer.builder()
                        .fullName("Juan Perez")
                        .state(CostumerStatus.ACTIVE)
                        .build()
        );

        assertThat(saved.getId()).isNotNull();
        assertThat(costomerRepository.findById(saved.getId())).isPresent();

        saved.setState(CostumerStatus.INACTIVE);
        Costumer updated = costomerRepository.save(saved);

        assertThat(updated.getState()).isEqualTo(CostumerStatus.INACTIVE);

        costomerRepository.deleteById(updated.getId());

        assertThat(costomerRepository.findById(updated.getId())).isEmpty();
    }
}