package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale save(Sale sale);

    Sale findSaleById(Long id);
    Sale findSaleByIdAndEnabled(Long id, boolean enabled);

    List<Sale> findAll();

    Page<Sale> findAll(Pageable pageable);
    Page<Sale> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

//    @Query("SELECT MAX(e.sequence) FROM Sale t")
//    int selectMaxSequence();

}
