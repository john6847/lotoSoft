package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Users;
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
    Enterprise findEnterpriseByName(String name);

    Page<Enterprise> findAllByEnabled(Pageable pageable, boolean state);
    Page<Enterprise> findAll(Pageable pageable);
    List<Enterprise> findAllByEnabled(Boolean enabled);

    void deleteById(Long id);

    Enterprise save(Enterprise enterprise);
    Enterprise findEnterpriseByIdentifier(String identifier);

    @Query("SELECT MAX(e.sequence) FROM Enterprise e")
    int selectMaxSequence();

//    @Query( value = "SELECT  from enterprise e order by e.id desc", nativeQuery =true)
    Enterprise findTopByOrderByIdDesc();

    String q = "SELECT * FROM enterprise e WHERE e.name IN (select e.NAME from enterprise e where e.name NOT IN (?1))";
    @Query(value = q, nativeQuery = true)
    List<Enterprise> selectAllEnterpriseExcept(String name);

}
