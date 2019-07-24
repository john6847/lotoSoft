package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Shift findShiftById(Long id);
    Shift findShiftByEnabledAndEnterpriseId (boolean state, Long enterpriseId);

    Shift save(Shift shift);
}
