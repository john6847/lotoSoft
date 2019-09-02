package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Shift findShiftByIdAndEnterpriseId(Long id, Long enterpriseId);
    Shift findShiftByNameAndEnterpriseId(String name, Long enterpriseId);
    List<Shift> findAllByEnterpriseId(Long enterpriseId);
    Shift findShiftByEnabledAndEnterpriseId (boolean state, Long enterpriseId);

    Shift save(Shift shift);
}
