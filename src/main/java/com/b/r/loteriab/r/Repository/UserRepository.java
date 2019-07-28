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
    Users findByUsername(String username);
    Users findUsersByUsernameAndEnterpriseName(String username, String enterprise);

    Users findUsersById(Long id);

    Users findUsersByToken(String token);

    Page<Users> findAll(Pageable pageable);


    List<Users>findAll();

    Page<Users> findAllByEnabled(Pageable pageable, boolean state);
    List<Users> findAllByEnabled(Boolean enabled);




    String q1 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id " +
            "WHERE u.id NOT IN (SELECT s.user_id FROM Seller s) and r.name = ?1 and u.enabled =?2";
    @Query(value = q1, nativeQuery = true)
    List<Users> selectUserByNameAndEnabled(String name, boolean enabled);

    String q2 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id " +
            "WHERE r.name=?1";
    @Query(value = q2, nativeQuery = true)
    List<Users> selectUserSuperAdmin(String name);

    String q3 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1 and order by ?#{#pageable}";
    @Query(value = q3, nativeQuery = true)
    Page<Users> selectUserSuperAdmin(String name, Pageable pageable);

    String q4 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id \n" +
            "WHERE r.name=?1  and u.enabled =?2 and order by ?#{#pageable}";
    @Query(value = q4, nativeQuery = true)
    Page<Users> selectUserSuperAdminAndEnabled(String name, boolean state, Pageable pageable);

    String q5 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2))";
    @Query(value = q5, nativeQuery = true)
    List<Users> selectAllUserExceptSuperAdmin(String name, String name1);

    String q6 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2)) and order by ?#{#pageable}";
    @Query(value = q6, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdmin(String name, String name1, Pageable pageable);

    String q7 = "SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id\n" +
            "WHERE r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2)) u.enabled =?3 and order by ?#{#pageable}";
    @Query(value = q7, nativeQuery = true)
    Page<Users> selectUserExceptSuperAdminAndEnabled(String name, String name1, boolean state, Pageable pageable);

//    SELECT * FROM users u INNER JOIN  users_roles ur ON ur.users_id = u.id INNER JOIN ROLE r ON r.id = ur.roles_id
//
//            WHERE
//--           r.NAME IN (select rol.NAME from role rol where rol.name NOT IN (?1, ?2))
//
//    r.Name NOT IN ('ROLE_SUPER_ADMIN', 'ROLE_SUPER_MEGA_ADMIN')
//
//    GROUP BY r.id, u.id;
//
//    HAVING COUNT(r.id) = 1

    void deleteById(Long id);
}
