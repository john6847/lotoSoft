package com.b.r.loteriab.r.Repository;

import com.b.r.loteriab.r.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Dany on 22/04/2019.
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    String q1 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE u.id NOT IN (SELECT s.user_id FROM Seller s where s.enterprise_id=?3) and r.name = ?1 and u.enabled =?2 and u.enterprise_id=?3";
    String q3 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1 ORDER BY u.id, ?#{#pageable}";
    String q4 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1  and u.enabled =?2 and order by u.id, ?#{#pageable}";
    String q5 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1  and u.enabled =?2 and order by u.id";
    String q6 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.enterprise_id=?3 and rol.name NOT IN (?1, ?2)) and u.enterprise_id=?3 ORDER BY u.id DESC, ?#{#pageable}";
    String q7 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.enterprise_id=?4 and rol.name NOT IN (?1, ?2)) and u.enabled =?3 and u.enterprise_id=?4 ORDER BY u.id DESC, ?#{#pageable}";
    String q8 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.enterprise_id=?4 and rol.name NOT IN (?1, ?2)) and u.enabled =?3 and u.enterprise_id=?4 ORDER BY u.id DESC";

    Users findByUsernameAndEnterpriseId(String username, Long enterpriseId);

    Users findByUsernameAndEnterpriseName(String username, String enterprise);

    Users findByUsername(String username);

    Users findUsersByIdAndEnterpriseId(Long id, Long enterpriseId);

    Users findUsersById(Long id);

    Page<Users> findAllByEnterpriseId(Pageable pageable, Long enterpriseId);

    List<Users> findAllByEnterpriseId(Long enterpriseId);

    Page<Users> findAllByEnabledAndEnterpriseId(Pageable pageable, boolean state, Long enterpriseId);

    List<Users> findAllByEnabledAndEnterpriseId(Boolean enabled, Long enterpriseId);

    @Query(value = q1, nativeQuery = true)
    List<Users> selectUserByNameAndEnabledAndEnterpriseId(String name, boolean enabled, Long enterpriseId);

    @Query(value = q3, nativeQuery = true)
    Page<Users> selectUserSuperAdmin(String name, Pageable pageable);

    @Query(value = q4, nativeQuery = true)
    Page<Users> selectUserSuperAdminAndEnabled(String name, boolean state, Pageable pageable);

    @Query(value = q5, nativeQuery = true)
    List<Users> selectListUserSuperAdminAndEnabled(String name, boolean state);

    @Query(value = q6, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdminAndEnterpriseId(String name, String name1, Long enterpriseId, Pageable pageable);

    @Query(value = q7, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdminAndEnabledAndEnterpriseId(String name, String name1, boolean state, Long enterpriseId, Pageable pageable);

    @Query(value = q8, nativeQuery = true)
    List<Users> selectUserExceptSuperAdminAndEnabledAndEnterpriseId(String name, String name1, boolean state, Long enterpriseId);

    void deleteByIdAndEnterpriseId(Long id, Long enterpriseId);
}
