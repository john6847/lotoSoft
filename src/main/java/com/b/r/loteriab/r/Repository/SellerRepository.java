package com.b.r.loteriab.r.Repository;


import com.b.r.loteriab.r.Model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerByIdAndEnterpriseId(Long id, Long enterpriseId);
    Seller findSellerByIdAndEnabledAndEnterpriseId(Long id,boolean enabled, Long enterpriseId);

    Page<Seller> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    Page<Seller> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);
    List<Seller> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);
    List<Seller> findAllByEnterpriseId(Long enterpriseId);

    Seller save(Seller seller);

    Seller findByUserIdAndEnterpriseId(Long id, Long enterpriseId);

    List<Seller> findAllByGroupsIdAndEnterpriseId(Long id, Long enterpriseId);

    void deleteSellerByIdAndEnterpriseId(Long id,Long enterpriseId);

    String q1 = "SELECT * FROM seller s, groups g\n" +
            " WHERE s.groups_id Is NULL \n" +
            " AND s.id NOT IN (SELECT gr.PARENT_SELLER_ID FROM groups gr)\n" +
            " AND s.enabled =?1 AND s.enterprise_id=?2";
    @Query(value = q1, nativeQuery = true)
    List<Seller> selectAllSellersByEnterpriseId(boolean enabled,Long enterpriseId);//
}
