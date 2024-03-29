package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Draw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface DrawRepository extends JpaRepository<Draw, Long> {

    String q1 = "SELECT * FROM draw d WHERE cast (d.modification_date as timestamp)  = date_trunc('day', ?2) and d.enabled = ?1 and d.enterprise_id =?3 and order by ?#{#pageable}";
    String q2 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and d.enabled = ?1 and d.enterprise_id=?3 and order by ?#{#pageable}";
    String q3 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and d.enterprise_id=?3 and order by ?#{#pageable}";
    String q4 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and d.enabled = ?1 and d.enterprise_id=?3 and  order by ?#{#pageable}";
    String q5 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and d.enterprise_id=?3 and  order by ?#{#pageable}";
    String q6 = "SELECT * FROM draw d WHERE and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and d.enterprise_id=?3 and  order by ?#{#pageable}";
    String q7 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?1) and d.enterprise_id=?2  and  order by ?#{#pageable}";
    String q8 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?1) and date(to_timestamp(d.modification_date)) = date_trunc('month', ?1) and d.enterprise_id=?2  and  order by ?#{#pageable}";
    String q9 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?1) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?1) and d.enterprise_id=?2  and  order by ?#{#pageable}";
    String q10 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?1) and d.enterprise_id=?2  and  order by ?#{#pageable}";
    String q11 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?1) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?1) and d.enterprise_id=?2 and  order by ?#{#pageable}";
    String q12 = "SELECT * FROM draw d WHERE and date(to_timestamp(d.modification_date)) = date_trunc('year', ?1) and d.enterprise_id=?2  and  order by ?#{#pageable}";

    Draw findDrawByIdAndEnterpriseId(Long id, Long enterpriseId);

    List<Draw> findAllByEnabledAndEnterpriseIdOrderByIdDesc(boolean enabled, Long enterpriseId);

    Page<Draw> findAllByEnabledAndEnterpriseIdOrderByIdDesc(boolean enabled, Long enterpriseId, Pageable pageable);

    Page<Draw> findAllByEnterpriseIdOrderByIdDesc(Pageable pageable, Long enterpriseId);

    List<Draw> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    Draw findDrawByShiftNameAndDrawDateAndEnterpriseId(String shiftName, Date date, Long enterpriseId);

    List<Draw> findAllByShiftNameAndDrawDateAndEnterpriseId(String shiftName, Date drawDate, Long enterpriseId);

    Draw findByDrawDateAndEnterpriseIdAndShiftId(Date drawDate, Long enterpriseId, Long shift_id);

    Draw save(Draw draw);

    List<Draw> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);

    List<Draw> findAllByEnabledAndModificationDateAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    List<Draw> findAllByModificationDateAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q1, nativeQuery = true)
    List<Draw> selectDrawByDayAndEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q2, nativeQuery = true)
    List<Draw> selectDrawByDayAndMonthAndEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q3, nativeQuery = true)
    List<Draw> selectDrawByDayAndYearEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q4, nativeQuery = true)
    List<Draw> selectDrawByMonthAndEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterprise_id, Pageable pageable);

    @Query(value = q5, nativeQuery = true)
    List<Draw> selectDrawByMonthAndYearEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q6, nativeQuery = true)
    List<Draw> selectDrawByYearAndEnabledAndEnterpriseId(boolean enabled, Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q7, nativeQuery = true)
    List<Draw> selectDrawByDayAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q8, nativeQuery = true)
    List<Draw> selectDrawByDayAndMonthAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q9, nativeQuery = true)
    List<Draw> selectDrawByDayAndYearAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q10, nativeQuery = true)
    List<Draw> selectDrawByMonthAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q11, nativeQuery = true)
    List<Draw> selectDrawByMonthAndYearAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);

    @Query(value = q12, nativeQuery = true)
    List<Draw> selectDrawByYearAndEnterpriseId(Date modificationDate, Long enterpriseId, Pageable pageable);


}
