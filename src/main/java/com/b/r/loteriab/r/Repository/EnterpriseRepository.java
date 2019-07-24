package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 14/07/2019.
 */
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    Enterprise findEnterpriseById(Long id);
    Enterprise findEnterpriseByEnabledAndId(boolean enabled, Long id);
    Pos findEnterpriseByName(String name);

    Page<Enterprise> findAllByEnabled(Pageable pageable, boolean state);
    Page<Enterprise> findAll(Pageable pageable);
    List<Enterprise> findAllByEnabled(Boolean enabled);

    void deleteById(Long id);

    Enterprise save(Enterprise enterprise);
    Enterprise findEnterpriseByIdentifier(String identifier);

    @Query("SELECT MAX(e.sequence) FROM Enterprise e")
    int selectMaxSequence();

}
