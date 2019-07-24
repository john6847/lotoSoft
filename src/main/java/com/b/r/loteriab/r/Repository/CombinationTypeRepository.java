package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.CombinationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface CombinationTypeRepository extends JpaRepository<CombinationType, Long> {

    CombinationType findCombinationTypeById(Long id);

    Page<CombinationType> findAllByEnabled(Pageable pageable, boolean enabled);

    Page<CombinationType> findAll(Pageable pageable);

    List<CombinationType> findAllByEnabled(boolean enabled);

    void deleteById(Long id);

    CombinationType findByProductsName(String name);

    CombinationType save(CombinationType combinationType);
}
