package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNameAndEnterpriseId(String username, Long enterpriseId);

    List<Role> findAllByEnterpriseId(Long enterpriseId);
}
