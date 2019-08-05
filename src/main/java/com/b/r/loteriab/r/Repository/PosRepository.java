package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Bank;
import com.b.r.loteriab.r.Model.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface PosRepository extends JpaRepository<Pos, Long> {

    Pos findPosByIdAndEnterpriseId(Long id, Long enterpriseId);
    Pos findPosByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);

    Page<Pos> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean state, Long enterpriseId);
    Page<Pos> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    List <Pos> findAllByEnterpriseId(Long enterpriseId);
    List<Pos> findAllByEnabledAndEnterpriseId(Boolean enabled,Long enterpriseId);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);

    Pos save (Pos pos);

    Pos findPosBySerialAndEnterpriseId (String serial, Long enterpriseId);
    Pos findBySerialAndEnabledAndEnterpriseId (String serial, boolean enabled, Long enterpriseId);
    Pos findByMacAddressAndEnterpriseId (String macAddress,Long enterpriseId);
    Pos findPosByDescriptionAndEnterpriseId(String description, Long enterpriseId);

    String q1 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT s.pos_id FROM Seller s where s.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1";
    @Query(value = q1, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPosByEnterpriseId(boolean enabled, Long enterpriseId);

    String q2 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT b.pos_id FROM Bank b where b.enterprise_id=?2) and p.enterprise_id=?2 and p.enabled =?1";
    @Query(value = q2, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPosForBankByEnterpriseId(boolean enabled, Long enterpriseId);


//    String q3 = "Select p.ID, p.DESCRIPTION from seller s right join pos p on p.id = s.POS_ID where s.id = ?1 and s.enterprise_id=?2 and s.enabled =?3";
    String q3 = "Select p from pos p where not exists( select 1 from seller s where s.pos_id = p.id and s.enterprise_id=?2) and s.enterprise_id=?2 and s.enabled =?3";
    @Query(value = q3, nativeQuery = true)
    List<Pos> selectPosRelatedToSeller(Long sellerId, Long enterpriseId, boolean enabled);

    String q4 ="SELECT p.ID, p.DESCRIPTION FROM Pos p WHERE p.id NOT IN (SELECT b.pos_id FROM Bank b where b.enterprise_id=?2) and p.id NOT IN (SELECT sel.pos_id FROM Seller sel where sel.enterprise_id=?2) and s.enterprise_id=?2 and s.enabled =?1";
    @Query(value = q4, nativeQuery = true)
    List<Pos> selectAllPosFreeFromBankAndByEnterpriseId(boolean enabled, Long enterpriseId);


}
