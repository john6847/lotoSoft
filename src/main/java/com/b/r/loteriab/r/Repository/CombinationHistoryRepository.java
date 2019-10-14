package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Address;
import com.b.r.loteriab.r.Model.CombinationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CombinationHistoryRepository extends JpaRepository<CombinationHistory, Long> {
    CombinationHistory save(CombinationHistory combinationHistory);
}


//    SELECT ch.*,
//        SUM (ch.sale_total),
//        cn.*
//        FROM combination_history ch
//        INNER JOIN combination cn ON cn.id = ch.id
//        INNER JOIN combination_type ct ON ct.id = cn.combination_type_id
//        where ch.combination_id = 1
//        GROUP BY ct.id, ch.id, cn.id;
