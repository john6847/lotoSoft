package com.b.r.loteriab.r.Repository;

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

    String q1 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT s.pos_id FROM Seller s where s.pos_id is not null and s.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1 and p.deleted = false";
    String q3 = "select * from pos p where p.id in (select s.pos_id from seller s where s.pos_id is not null and s.id=?1 and s.enterprise_id=?2)\n" +
            "and p.enterprise_id=?2 and p.enabled = ?3 and p.deleted = false";
    String q4 = "SELECT * FROM Pos p WHERE p.id NOT IN\n" +
            "(SELECT b.pos_id FROM Bank b where b.pos_id is not null and b.enterprise_id=?2) and p.id NOT IN\n" +
            "(SELECT sel.pos_id FROM Seller sel where sel.pos_id is not null and sel.enterprise_id=?2)\n" +
            "and p.enterprise_id=?2 and p.enabled =?1 and p.deleted = false";

    Pos findPosByIdAndEnterpriseIdAndDeletedFalse(Long id, Long enterpriseId);

    Page<Pos> findAllByEnabledAndDeletedFalseAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean state, Long enterpriseId);

    Page<Pos> findAllByEnterpriseIdAndDeletedFalseOrderByIdDesc(Pageable pageable, Long enterpriseId);

    Pos save(Pos pos);

    Pos findPosBySerialAndEnterpriseIdAndDeletedFalse(String serial, Long enterpriseId);

    Pos findPosByDescriptionAndEnterpriseIdAndDeletedFalse(String description, Long enterpriseId);

    Pos findBySerialAndEnabledAndEnterpriseIdAndDeletedFalse(String serial, boolean enabled, Long enterpriseId);

    @Query(value = q1, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPosByEnterpriseId(boolean enabled, Long enterpriseId);

    @Query(value = q3, nativeQuery = true)
    List<Pos> selectPosRelatedToSeller(Long sellerId, Long enterpriseId, boolean enabled);

    @Query(value = q4, nativeQuery = true)
    List<Pos> selectAllPosFreeFromBankAndByEnterpriseId(boolean enabled, Long enterpriseId);


}
