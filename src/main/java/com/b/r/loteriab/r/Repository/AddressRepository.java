package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address save (Address address);
}
