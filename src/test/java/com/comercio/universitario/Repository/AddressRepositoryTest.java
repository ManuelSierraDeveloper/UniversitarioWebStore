package com.comercio.universitario.Repository;

import com.comercio.universitario.Entitys.Address;
import com.comercio.universitario.Entitys.Costumer;
import com.comercio.universitario.Entitys.Enums.CostumerStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class AddressRepositoryTest extends AbstractRepositoryIT {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CostomerRepository costomerRepository;

    @Test
    void shouldCreateReadUpdateDeleteAddress() {
        Costumer customer = costomerRepository.save(
                Costumer.builder()
                        .fullName("Maria Gomez")
                        .state(CostumerStatus.ACTIVE)
                        .build()
        );

        Address saved = addressRepository.save(
                Address.builder()
                        .address("Calle 123")
                        .reference("Frente al parque")
                        .costumer(customer)
                        .build()
        );

        assertThat(saved.getId()).isNotNull();
        assertThat(addressRepository.findById(saved.getId())).isPresent();

        saved.setReference("Segundo piso");
        Address updated = addressRepository.save(saved);

        assertThat(updated.getReference()).isEqualTo("Segundo piso");

        addressRepository.deleteById(updated.getId());

        assertThat(addressRepository.findById(updated.getId())).isEmpty();
    }
}