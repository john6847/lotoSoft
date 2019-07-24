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

    Seller findSellerById(Long id);
    Seller findSellerByIdAndEnabled(Long id,boolean enabled);

//    Seller findByName(String name);
//    Seller findByNameAndEnabled(String name, boolean enabled);
    Page<Seller> findAll(Pageable pageable);
    Page<Seller> findAllByEnabled(Pageable pageable, boolean enabled);
    List<Seller> findAllByEnabled(boolean enabled);

    Seller save(Seller seller);

    Seller findByUserId(Long id);

    List<Seller> findAllByGroupsId(Long id);

    void deleteSellerById(Long id);

    String q1 = "SELECT * FROM seller s, groups g\n" +
            " WHERE s.groups_id Is NULL \n" +
            " AND s.id NOT IN (SELECT gr.PARENT_SELLER_ID FROM groups gr)\n" +
            " AND s.enabled =?1";
    @Query(value = q1, nativeQuery = true)
    List<Seller> selectAllSellers(boolean enabled);//
}
