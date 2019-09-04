package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Interaces.IReportViewModel;
import com.b.r.loteriab.r.Model.Sale;
import com.b.r.loteriab.r.Model.Shift;
import com.b.r.loteriab.r.Model.Users;
import com.b.r.loteriab.r.Model.ViewModel.SalesReportViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Sale save(Sale sale);

    Sale findSaleByIdAndEnterpriseId(Long id, Long enterpriseId);
    Sale findSaleByTicketSerialAndEnterpriseId(String id, Long enterpriseId);
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
    void deleteSaleByIdAndEnterpriseId(Long id, Long enterpriseId);

    String q1 = "select * from sale s where s.enterprise_id = ?1 and s.shift_id = ?2 and s.date >= ?3 and s.date <= ?4";
    @Query(value = q1, nativeQuery = true)
    List<Sale> selectAllSale(Long enterpriseId, Long shiftId, Date startDate, Date endDate); // TODO: Check query



//    http://www.sqlservertutorial.net/sql-server-aggregate-functions/sql-server-sum/
String q2 = "SELECT\n" +
        "    u.name as sellerName,\n"+
        "    s.seller_id  as sellerId,\n" +
        "    SUM(s.total_amount) as  saleTotal,\n" +
        "    SUM(t.amount_won) as amountWon,\n" +
        "    (SUM(s.total_amount)- SUM(t.amount_won))as netSale,\n" +
        "            CASE\n" +
        "    WHEN s2.amount_charged > 0 THEN s2.amount_charged\n" +
        "    WHEN s2.percentage_charged > 0 THEN (SUM((s.total_amount * (s2.percentage_charged / 100))))\n" +
        "    END AS salary,\n" +
        "    (SUM(s.total_amount)- SUM(t.amount_won) - SUM((s.total_amount * (s2.percentage_charged / 100)))) as saleResult\n" +
        "\n" +
        "    FROM\n" +
        "    sale s\n" +
        "    INNER JOIN ticket t on s.ticket_id = t.id\n" +
        "    INNER JOIN seller s2 on s.seller_id = s2.id\n" +
        "    INNER JOIN users u on u.id = s2.user_id\n"+
        "\n" +
        "    where s.enterprise_id = :enterpriseId and s.shift_id = :shiftId\n" +
        "    and cast (s.date as timestamp)\n" +
        "    BETWEEN to_timestamp(:startDate, 'YYYY-MM-DD HH24:MI:SS')\n" +
        "    AND to_timestamp(:endDate, 'YYYY-MM-DD HH24:MI:SS')\n" +
        "    GROUP BY\n" +
        "    s.seller_id, s2.percentage_charged, s2.amount_charged, u.name";
    @Query(value = q2, nativeQuery = true)
    List<IReportViewModel> selectSaleReportGloballyOverAPeriod(Long enterpriseId, Long shiftId, String startDate, String endDate);

    String q3 = "SELECT s.seller_id as sellerId,\n" +
            "    u.name as sellerName,\n"+
            "    SUM(s.total_amount) as saleTotal,\n" +
            "    SUM(t.amount_won) as amountWon,\n" +
            "    (SUM(s.total_amount) - SUM(t.amount_won))as netSale,\n" +
            "    CASE\n" +
            "    WHEN s2.amount_charged > 0 THEN s2.amount_charged\n" +
            "    WHEN s2.percentage_charged > 0 THEN (SUM((s.total_amount * (s2.percentage_charged / 100))))\n" +
            "    END AS salary,\n" +
            "    (SUM(s.total_amount)- SUM(t.amount_won) - SUM((s.total_amount * (s2.percentage_charged / 100)))) as saleResult\n" +
            "\n" +
            "    FROM\n" +
            "    sale s\n" +
            "    INNER JOIN ticket t on s.ticket_id = t.id\n" +
            "    INNER JOIN seller s2 on s.seller_id = s2.id\n" +
            "    INNER JOIN users u on u.id = s2.user_id"+
            "\n" +
            "    where s.enterprise_id = :enterpriseId and s.shift_id = :shiftId and s.seller_id = :sellerId\n" +
            "    and cast (s.date as timestamp)\n" +
            "    BETWEEN to_timestamp(:startDate, 'YYYY-MM-DD HH24:MI:SS')\n" +
            "    AND to_timestamp(:endDate, 'YYYY-MM-DD HH24:MI:SS')\n" +
            "    GROUP BY\n" +
            "    s.seller_id, s2.percentage_charged, s2.amount_charged, u.name";
    @Query(value = q3, nativeQuery = true)
    List<IReportViewModel> selectSaleReportGloballyOverAPeriodBySeller(Long enterpriseId, Long shiftId, Long sellerId, String startDate, String endDate);






}
