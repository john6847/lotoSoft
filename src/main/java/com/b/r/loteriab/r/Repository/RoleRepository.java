package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName (String username);

    List<Role> findAll();

    void deleteRolById(Long id);
}
