package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale save(Sale sale);

    Sale findSaleByIdAndEnterpriseId(Long id, Long enterpriseId);
    Sale findSaleByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);
    Sale findSaleByTicketIdAndEnterpriseId(Long ticketId, Long enterpriseId);

    List<Sale> findAllByEnterpriseId(Long enterpriseId);
    List<Sale> findAllByEnterpriseIdAndDateAndShiftId(Long enterpriseId, Date date, Long shiftId);

    Page<Sale> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    Page<Sale> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

//    @Query("SELECT MAX(e.sequence) FROM Sale t")
//    int selectMaxSequence();

    List<Sale> findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(Long enterpriseId, Long sellerId, Long shiftId, Date startDate, Date endDate);
    List<Sale> findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftId(Long enterpriseId, Long sellerId, Long shifId);

    void deleteSaleByTicketIdAndEnterpriseId(Long ticketId, Long enterpriseId);
}
