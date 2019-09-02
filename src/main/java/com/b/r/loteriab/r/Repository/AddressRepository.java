package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address save (Address address);
}
