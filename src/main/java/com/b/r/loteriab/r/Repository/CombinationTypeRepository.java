package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.CombinationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface CombinationTypeRepository extends JpaRepository<CombinationType, Long> {

    CombinationType findCombinationTypeByIdAndEnterpriseId(Long id, Long enterpriseId);

    Page<CombinationType> findAllByEnabledAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean enabled, Long enterpriseId);

    Page<CombinationType> findAllByEnterpriseIdOrderByIdDesc(Pageable pageable, Long enterpriseId);

    List<CombinationType> findAllByEnterpriseName(String enterpriseName);

    List<CombinationType> findAllByEnabledAndEnterpriseIdOrderByIdDesc(boolean enabled, Long enterpriseId);

    List<CombinationType> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    CombinationType findByProductsNameAndEnterpriseId(String name, Long enterpriseId);

    CombinationType save(CombinationType combinationType);
}
