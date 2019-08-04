package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Groups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    Groups findGroupsByIdAndEnterpriseId(Long id, Long enterpriseId);
    Groups findGroupsByIdAndEnabledAndEnterpriseId(Long id, boolean enabled, Long enterpriseId);
    Page<Groups> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);
    List<Groups> findAllByEnterpriseId(Long enterpriseId);
    Page<Groups> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean enabled, Long enterpriseId);
    List<Groups> findAllByEnabledAndEnterpriseId(boolean enabled, Long enterpriseId);


    void deleteById(Long id);

    Groups findByParentSellerIdAndEnterpriseId(Long id, Long enterpriseId);

    Groups save(Groups groups);
}
