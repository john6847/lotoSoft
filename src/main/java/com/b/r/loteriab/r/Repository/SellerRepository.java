package com.b.r.loteriab.r.Repository;


import com.b.r.loteriab.r.Model.Seller;
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
@Transactional
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    String q1 = "SELECT * FROM seller s\n" +
            " WHERE s.groups_id Is NULL \n" +
            " AND s.id NOT IN (SELECT gr.PARENT_SELLER_ID FROM groups gr where gr.enterprise_id=?2)\n" +
            " AND s.enabled =?1 AND s.deleted = false AND s.enterprise_id=?2";
    String q2 = "SELECT * FROM seller s WHERE s.id NOT IN (SELECT b.seller_id FROM Bank b  where b.enterprise_id=?2) AND s.enterprise_id=?2 AND s.deleted = false AND s.enabled =?1";
    String q3 = "SELECT * FROM seller s\n" +
            " WHERE s.groups_id = ?1\n" +
            " AND s.id IN (SELECT gr.PARENT_SELLER_ID FROM groups gr where gr.enterprise_id=?3)\n" +
            " AND s.enabled =?2 AND s.enterprise_id=?3 AND s.deleted = false";

    Seller findSellerByIdAndEnterpriseIdAndDeletedFalse(Long id, Long enterpriseId);

    Seller findSellerByPosIdAndEnterpriseIdAndDeletedFalse(Long posId, Long enterpriseId);

    Seller findSellerByUserIdAndDeletedFalse(Long id);

    Seller findSellerByUserIdAndDeletedFalseAndEnterpriseId(Long id, Long enterpriseId);

    Page<Seller> findAllByEnterpriseIdAndDeletedFalseOrderByIdDesc(Pageable pageable, Long enterpriseId);

    Page<Seller> findAllByEnabledAndDeletedFalseAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean enabled, Long enterpriseId);

    List<Seller> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);

    List<Seller> findAllByEnterpriseIdAndDeletedFalse(Long enterpriseId);

    Seller save(Seller seller);

    Seller findByUserIdAndDeletedFalseAndEnterpriseId(Long id, Long enterpriseId);

    List<Seller> findAllByGroupsIdAndEnterpriseIdAndDeletedFalse(Long id, Long enterpriseId);

    void deleteSellerByIdAndEnterpriseId(Long id, Long enterpriseId);

    @Query(value = q1, nativeQuery = true)
    List<Seller> selectAllSellersByEnterpriseId(boolean enabled, Long enterpriseId);

    @Query(value = q2, nativeQuery = true)
    List<Seller> selectAllSellersFreeFromBankByEnterpriseId(boolean enabled, Long enterpriseId);

    @Query(value = q3, nativeQuery = true)
    List<Seller> selectAllSellersInGroupsByEnterpriseId(Long groups_id, boolean enabled, Long enterpriseId);
}
