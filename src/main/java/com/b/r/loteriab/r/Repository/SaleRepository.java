package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Sale;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Model.ViewModel.SalesReportViewModel;
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
String q2 = " SELECT\n" +
        "    s.seller_id,\n" +
        "    SUM(s.total_amount) as  sale_total,\n" +
        "    SUM(t.amount_won) as amount_won,\n" +
        "    (SUM(s.total_amount)- SUM(t.amount_won))as net_sale,\n" +
        "            CASE\n" +
        "    WHEN s2.amount_charged > 0 THEN s2.amount_charged\n" +
        "    WHEN s2.percentage_charged > 0 THEN (SUM((s.total_amount * (s2.percentage_charged / 100))))\n" +
        "    END AS salary,\n" +
        "            (SUM(s.total_amount)- SUM(t.amount_won) - SUM((s.total_amount * (s2.percentage_charged / 100)))) as sale_result\n" +
        "\n" +
        "    FROM\n" +
        "    sale s\n" +
        "    INNER JOIN ticket t on s.ticket_id = t.id\n" +
        "    INNER JOIN seller s2 on s.seller_id = s2.id\n" +
        "\n" +
        "    where s.enterprise_id = ?1 and s.shift_id = ?2 and s.date between ?3 and ?4\n" +
        "    GROUP BY\n" +
        "    s.seller_id, s2.percentage_charged, s2.amount_charged";
    @Query(value = q2, nativeQuery = true)
    List<SalesReportViewModel> selectSaleReportGloballyOverAPeriod(Long enterpriseId, Long shiftId, Date startDate, Date endDate);

    String q3 = "SELECT\n" +
        "    s.seller_id,\n" +
        "    SUM(s.total_amount) as sale_total,\n" +
        "    SUM(t.amount_won) as amount_won,\n" +
        "    (SUM(s.total_amount) - SUM(t.amount_won))as net_sale,\n" +
        "    CASE\n" +
        "    WHEN s2.amount_charged > 0 THEN s2.amount_charged\n" +
        "    WHEN s2.percentage_charged > 0 THEN (SUM((s.total_amount * (s2.percentage_charged / 100))))\n" +
        "    END AS salary,\n" +
        "    (SUM(s.total_amount)- SUM(t.amount_won) - SUM((s.total_amount * (s2.percentage_charged / 100)))) as sale_result\n" +
        "\n" +
        "    FROM\n" +
        "    sale s\n" +
        "    INNER JOIN ticket t on s.ticket_id = t.id\n" +
        "    INNER JOIN seller s2 on s.seller_id = s2.id\n" +
        "\n" +
        "    where s.enterprise_id =?1 and s.shift_id =?2 and s.seller_id=?3 and s.date between ?4 and ?5\n" +
        "    GROUP BY\n" +
        "    s.seller_id, s2.percentage_charged, s2.amount_charged";
    @Query(value = q2, nativeQuery = true)
    List<SalesReportViewModel> selectSaleReportGloballyOverAPeriodBySeller(Long enterpriseId, Long shiftId, Long seller_id, Date startDate, Date endDate);






}
