package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsernameAndEnterpriseId(String username, Long enterpriseId);
    Users findByUsernameAndEnterpriseName(String username, String enterprise);
    Users findByUsername(String username);

    Users findUsersByTokenAndEnterpriseId(String token, Long enterpriseId);

    Users findUsersByIdAndEnterpriseId(Long id, Long enterpriseId);

    Page<Users> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);

    List<Users>findAllByEnterpriseId(Long enterpriseId);

    Page<Users> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean state, Long enterpriseId);
    List<Users> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId);


    String q1 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id " +
            "WHERE u.id NOT IN (SELECT s.user_id FROM Seller s) and r.name = ?1 and u.enabled =?2 and u.enterprise_id=?3";
    @Query(value = q1, nativeQuery = true)
    List<Users> selectUserByNameAndEnabledAndEnterpriseId(String name, boolean enabled, Long enterpriseId);

    String q2 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id " +
            "WHERE r.name=?1";
    @Query(value = q2, nativeQuery = true)
    List<Users> selectUserSuperAdmin(String name);

    String q3 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1 and order by ?#{#pageable}";
    @Query(value = q3, nativeQuery = true)
    Page<Users> selectUserSuperAdmin(String name,  Pageable pageable);

    String q4 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1  and u.enabled =?2 and order by ?#{#pageable}";
    @Query(value = q4, nativeQuery = true)
    Page<Users> selectUserSuperAdminAndEnabled(String name, boolean state, Pageable pageable);

    String q5 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2)) and u.enterprise_id =?3";
    @Query(value = q5, nativeQuery = true)
    List<Users> selectAllUserExceptSuperAdminAndEnterpriseId(String name, String name1, Long enterpriseId);

    String q6 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2)) and enterprise_id=?3 and order by ?#{#pageable}";
    @Query(value = q6, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdminAndEnterpriseId(String name, String name1, Long enterpriseId, Pageable pageable);

    String q7 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2)) u.enabled =?3 and enterprise_id=?4 and order by ?#{#pageable}";
    @Query(value = q7, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdminAndEnabledAndEnterpriseId(String name, String name1, boolean state, Long enterpriseId, Pageable pageable);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);
}
