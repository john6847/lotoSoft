package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
@Transactional
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    Groups findGroupsByIdAndEnterpriseId(Long id, Long enterpriseId);

    Groups findGroupsByParentSellerIdAndEnterpriseId(Long parentSellerId, Long enterpriseId);

    Page<Groups> findAllByEnterpriseIdOrderByIdDesc(Pageable pageable, Long enterpriseId);

    List<Groups> findAllByEnterpriseIdOrderByIdDesc(Long enterpriseId);

    Page<Groups> findAllByEnabledAndEnterpriseIdOrderByIdDesc(Pageable pageable, boolean enabled, Long enterpriseId);

    List<Groups> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);

    void deleteById(Long id);

    Groups findByParentSellerIdAndEnterpriseId(Long id, Long enterpriseId);

    Groups save(Groups groups);
}
