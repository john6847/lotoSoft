package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface PosRepository extends JpaRepository<Pos, Long> {

    Pos findPosByIdAndEnterpriseId(Long id, Long enterpriseId);

    Pos findPosByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);

    Page<Pos> findAllByEnabledAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean state, Long enterpriseId);

    Page<Pos> findAllByEnterpriseIdOrderByIdDesc(Pageable pageable, Long enterpriseId);

    List <Pos> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    List<Pos> findAllByEnabledAndEnterpriseIdOrderByIdDesc(Boolean enabled,Long enterpriseId);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);

    Pos save (Pos pos);

    Pos findPosBySerialAndEnterpriseId (String serial, Long enterpriseId);

    Pos findPosByDescriptionAndEnterpriseId (String description, Long enterpriseId);

    Pos findBySerialAndEnabledAndEnterpriseId (String serial, boolean enabled, Long enterpriseId);

    String q1 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT s.pos_id FROM Seller s where s.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1";
    @Query(value = q1, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPosByEnterpriseId(boolean enabled, Long enterpriseId);

    String q2 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT b.pos_id FROM Bank b where b.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1";
    @Query(value = q2, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPosForBankByEnterpriseId(boolean enabled, Long enterpriseId);


    String q3 = "select * from pos p where p.id in (select s.pos_id from seller s where s.id=?1 and s.enterprise_id=?2)\n" +
        "and p.enterprise_id=?2 and p.enabled = ?3";
    @Query(value = q3, nativeQuery = true)
    List<Pos> selectPosRelatedToSeller(Long sellerId, Long enterpriseId, boolean enabled);

    String q4 ="SELECT * FROM Pos p WHERE p.id NOT IN (SELECT b.pos_id FROM Bank b where b.enterprise_id=?2) and p.id NOT IN (SELECT sel.pos_id FROM Seller sel where sel.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1";
    @Query(value = q4, nativeQuery = true)
    List<Pos> selectAllPosFreeFromBankAndByEnterpriseId(boolean enabled, Long enterpriseId);


}
