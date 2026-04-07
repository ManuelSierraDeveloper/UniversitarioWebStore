package com.comercio.universitario.Repository;
import com.comercio.universitario.Entitys.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
