package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 14/07/2019.
 */
@Repository
@Transactional
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    String q = "SELECT * FROM enterprise e WHERE e.name IN (select e.NAME from enterprise e where e.name NOT IN (?1))";

    Enterprise findEnterpriseById(Long id);

    Enterprise findEnterpriseByEnabledAndNameIgnoreCase(boolean enabled, String enterpriseName);

    Enterprise findEnterpriseByNameContainingIgnoreCase(String name);

    Enterprise findEnterpriseBySubDomain(String subDomain);

    Page<Enterprise> findAllByEnabledOrderByIdDesc(Pageable pageable, boolean state);

    Page<Enterprise> findAllByOrderById(Pageable pageable);

    void deleteById(Long id);

    Enterprise save(Enterprise enterprise);

    @Query("SELECT MAX(e.sequence) FROM Enterprise e")
    int selectMaxSequence();

    Enterprise findTopByOrderByIdDesc();

    @Query(value = q, nativeQuery = true)
    List<Enterprise> selectAllEnterpriseExcept(String name);

}
