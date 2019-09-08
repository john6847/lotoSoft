package com.b.r.loteriab.r.Repository;


import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerByIdAndEnterpriseId(Long id, Long enterpriseId);
    Seller findSellerByIdAndEnabledAndEnterpriseId(Long id,boolean enabled, Long enterpriseId);

    Page<Seller> findAllByEnterpriseIdOrderByIdDesc(Pageable pageable, Long enterpriseId);
    Page<Seller> findAllByEnabledAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean enabled, Long enterpriseId);
    List<Seller> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);
    List<Seller> findAllByEnterpriseId(Long enterpriseId);

    Seller save(Seller seller);

    Seller findByUserIdAndEnterpriseId(Long id, Long enterpriseId);

    List<Seller> findAllByGroupsIdAndEnterpriseId(Long id, Long enterpriseId);

    void deleteSellerByIdAndEnterpriseId(Long id,Long enterpriseId);

    String q1 = "SELECT * FROM seller s\n" +
            " WHERE s.groups_id Is NULL \n" +
            " AND s.id NOT IN (SELECT gr.PARENT_SELLER_ID FROM groups gr where gr.enterprise_id=?2)\n" +
            " AND s.enabled =?1 AND s.enterprise_id=?2";
    @Query(value = q1, nativeQuery = true)
    List<Seller> selectAllSellersByEnterpriseId(boolean enabled,Long enterpriseId);


    String q2 ="SELECT * FROM seller s WHERE s.id NOT IN (SELECT b.seller_id FROM Bank b  where b.enterprise_id=?2) and s.enterprise_id=?2 and s.enabled =?1";
    @Query(value = q2, nativeQuery = true)
    List<Seller> selectAllSellersFreeFromBankByEnterpriseId(boolean enabled, Long enterpriseId);

    String q3 = "SELECT * FROM seller s\n" +
            " WHERE s.groups_id = ?1\n" +
            " AND s.id IN (SELECT gr.PARENT_SELLER_ID FROM groups gr where gr.enterprise_id=?3)\n" +
            " AND s.enabled =?2 AND s.enterprise_id=?3";
    @Query(value = q3, nativeQuery = true)
    List<Seller> selectAllSellersInGroupsByEnterpriseId(Long groups_id, boolean enabled,Long enterpriseId);
}
