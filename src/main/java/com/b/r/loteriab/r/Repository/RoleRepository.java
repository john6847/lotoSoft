package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNameAndEnterpriseId (String username, Long enterpriseId);
    Role findByName (String username);

    List<Role> findAllByEnterpriseId(Long enterpriseId);

    void deleteRolByIdAndEnterpriseId(Long id, Long enterpriseId);

//    @Query(nativeQuery =true, value = "select * from role e order by e.id desc")
//    Role findTopByOrderByIdDesc ();
}
