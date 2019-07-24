package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Draw;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

    Draw findDrawById(Long id);

    List<Draw> findAllByEnabled(boolean enabled);

    void deleteById(Long id);

    Draw findDrawByShiftNameAndDrawDate(String shiftName,Date date);


    List<Draw>findAllByShiftNameAndDrawDate(String shiftName,Date drawDate);

    Draw save(Draw draw);

    List<Draw> findAllByEnabled(Pageable pageable,  boolean enabled);

    List<Draw> findAllByEnabledAndModificationDate( boolean enabled, Date modificationDate, Pageable pageable);

    List<Draw> findAllByModificationDate( Date modificationDate, Pageable pageable);

    String q1 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q1, nativeQuery = true)
    List<Draw> selectDrawByDayAndEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q2 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q2, nativeQuery = true)
    List<Draw> selectDrawByDayAndMonthAndEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q3 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q3, nativeQuery = true)
    List<Draw> selectDrawByDayAndYearEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q4 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q4, nativeQuery = true)
    List<Draw> selectDrawByMonthAndEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q5 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q5, nativeQuery = true)
    List<Draw> selectDrawByMonthAndYearEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q6 = "SELECT * FROM draw d WHERE and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2) and d.enabled = ?1 and  order by ?#{#pageable}";
    @Query(value = q6, nativeQuery = true)
    List<Draw> selectDrawByYearAndEnabled(boolean enabled, Date modificationDate, Pageable pageable);

    String q7 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2)  and  order by ?#{#pageable}";
    @Query(value = q7, nativeQuery = true)
    List<Draw> selectDrawByDay( Date modificationDate, Pageable pageable);

    String q8 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('month', ?2)  and  order by ?#{#pageable}";
    @Query(value = q8, nativeQuery = true)
    List<Draw> selectDrawByDayAndMonth( Date modificationDate, Pageable pageable);

    String q9 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('day', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2)  and  order by ?#{#pageable}";
    @Query(value = q9, nativeQuery = true)
    List<Draw> selectDrawByDayAndYear( Date modificationDate, Pageable pageable);

    String q10 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2)  and  order by ?#{#pageable}";
    @Query(value = q10, nativeQuery = true)
    List<Draw> selectDrawByMonth( Date modificationDate, Pageable pageable);

    String q11 = "SELECT * FROM draw d WHERE date(to_timestamp(d.modification_date)) = date_trunc('month', ?2) and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2)  and  order by ?#{#pageable}";
    @Query(value = q11, nativeQuery = true)
    List<Draw> selectDrawByMonthAndYear( Date modificationDate, Pageable pageable);

    String q12 = "SELECT * FROM draw d WHERE and date(to_timestamp(d.modification_date)) = date_trunc('year', ?2)  and  order by ?#{#pageable}";
    @Query(value = q12, nativeQuery = true)
    List<Draw> selectDrawByYear( Date modificationDate, Pageable pageable);


}
