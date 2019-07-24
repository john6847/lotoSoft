package com.b.r.loteriab.r.Repository;

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

    Pos findPosById(Long id);
    Pos findPosByIdAndEnabled(Long id, boolean enabled);

    Page<Pos> findAllByEnabled(Pageable pageable, boolean state);
    Page<Pos> findAll(Pageable pageable);
    List<Pos> findAllByEnabled(Boolean enabled);

    void deleteById(Long id);

    Pos save (Pos pos);

    Pos findPosBySerial (String serial);
    Pos findBySerialAndEnabled (String serial, boolean enabled);
    Pos findByMacAddress (String macAddress);
//    Control if that description exist for an enterprise pos
    Pos findPosByDescriptionAndEnterpriseId(String description, Long enterpriseId);

    String q1 = "SELECT * FROM pos p WHERE p.id NOT IN (SELECT s.pos_id FROM Seller s) and p.enabled =?1";
    @Query(value = q1, nativeQuery = true)
    List<Pos> selectAllFreeAndEnabledPos(boolean enabled);
}
