package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Sale;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale save(Sale sale);

    Sale findSaleByIdAndEnterpriseId(Long id, Long enterpriseId);
    Sale findSaleByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);
    Sale findSaleByTicketIdAndEnterpriseId(Long ticketId, Long enterpriseId);
    Sale findSaleByTicketIdAndEnterpriseIdAndSellerId(Long ticketId, Long enterpriseId, Long sellerId);

    List<Sale> findAllByEnterpriseId(Long enterpriseId);
    List<Sale> findAllByEnterpriseIdAndDateAndShiftId(Long enterpriseId, Date date, Long shiftId);

    Page<Sale> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    Page<Sale> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

//    @Query("SELECT MAX(e.sequence) FROM Sale t")
//    int selectMaxSequence();

    List<Sale> findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdAndShiftIdAndDateAfterAndDateBefore(Long enterpriseId, Long sellerId, Long shiftId, Date startDate, Date endDate);
    List<Sale> findAllByTicket_WonTrueAndEnterpriseIdAndSellerIdOrderByShiftIdDesc(Long enterpriseId, Long sellerId);

    void deleteSaleByTicketIdAndEnterpriseId(Long ticketId, Long enterpriseId);

    String q1 = "select * from sale s where s.enterprise_id = ?1 and s.shift_id = ?2 and s.date >= ?3 and s.date <= ?4";
    @Query(value = q1, nativeQuery = true)
    List<Sale> selectAllSale(Long enterpriseId, Long shiftId, Date startDate, Date endDate); // TODO: Check query



//    http://www.sqlservertutorial.net/sql-server-aggregate-functions/sql-server-sum/
//    SELECT
//    s.seller_id,
//    SUM(s.total_amount) as  sale_total,
//    SUM(t.amount_won) as amount_won,
//    (SUM(s.total_amount)- SUM(t.amount_won))as net_sale,
//            CASE
//    WHEN s2.amount_charged > 0 THEN s2.amount_charged
//    WHEN s2.percentage_charged > 0 THEN (SUM((s.total_amount * (s2.percentage_charged / 100))))
//    END AS salary,
//            (SUM(s.total_amount)- SUM(t.amount_won) - SUM((s.total_amount * (s2.percentage_charged / 100))) )
//
//    FROM
//    sale s
//    INNER JOIN ticket t on s.ticket_id = t.id
//    INNER JOIN seller s2 on s.seller_id = s2.id
//    GROUP BY
//    s.seller_id, s2.percentage_charged, s2.amount_charged;

}
